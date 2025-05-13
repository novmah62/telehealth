package com.drewsec.consultation_service.service.impl;

import com.drewsec.commons.exception.BadRequestException;
import com.drewsec.commons.exception.ResourceNotFoundException;
import com.drewsec.consultation_service.dto.response.ConsultationResponse;
import com.drewsec.consultation_service.dto.response.NotificationResponse;
import com.drewsec.consultation_service.entity.Consultation;
import com.drewsec.consultation_service.enumType.ConsultationStatus;
import com.drewsec.consultation_service.enumType.MessageType;
import com.drewsec.consultation_service.enumType.NotificationType;
import com.drewsec.consultation_service.mapper.ConsultationMapper;
import com.drewsec.consultation_service.repository.ConsultationRepository;
import com.drewsec.consultation_service.service.ConsultationService;
import com.drewsec.consultation_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.drewsec.commons.definitions.constants.ApiConstants.CONSULTATION_ALREADY_CLOSED;
import static com.drewsec.commons.definitions.constants.ApiConstants.INVALID_CONSULTATION_ACCESS;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final NotificationService notificationService;

    @Override
    public ConsultationResponse createConsultation(UUID patientId) {
        Consultation consultation = new Consultation();
        consultation.setPatientId(patientId);
        consultation.setStatus(ConsultationStatus.PENDING);
        Consultation saved = consultationRepository.save(consultation);
        return consultationMapper.toConsultationResponse(saved);
    }

    @Override
    public ConsultationResponse acceptConsultation(UUID consultationId, UUID consultantId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "consultation ID", consultationId.toString()));

        if (consultation.getStatus() != ConsultationStatus.PENDING) {
            throw new BadRequestException(CONSULTATION_ALREADY_CLOSED);
        }

        consultation.setConsultantId(consultantId);
        consultation.setStatus(ConsultationStatus.ACTIVE);
        consultation.setStartedAt(LocalDateTime.now());
        Consultation updated = consultationRepository.save(consultation);
        return consultationMapper.toConsultationResponse(updated);
    }

    @Override
    public ConsultationResponse completeConsultation(UUID consultationId, UUID userId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "consultation ID", consultationId.toString()));
        boolean isParticipant = userId.equals(consultation.getPatientId()) ||
                userId.equals(consultation.getConsultantId());
        if (!isParticipant) {
            throw new BadRequestException(INVALID_CONSULTATION_ACCESS);
        }
        if (consultation.getStatus() == ConsultationStatus.COMPLETED) {
            return consultationMapper.toConsultationResponse(consultation);
        }
        consultation.setStatus(ConsultationStatus.COMPLETED);
        consultation.setEndedAt(LocalDateTime.now());
        Consultation finished = consultationRepository.save(consultation);
        return consultationMapper.toConsultationResponse(finished);
    }

    @Override
    public List<ConsultationResponse> getConsultationsByPatient(UUID patientId) {
        return consultationRepository.findByPatientId(patientId)
                .stream()
                .map(consultationMapper::toConsultationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultationResponse> getConsultationsByConsultant(UUID consultantId) {
        return consultationRepository.findByConsultantId(consultantId)
                .stream()
                .map(consultationMapper::toConsultationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultationResponse> getPendingConsultations() {
        return consultationRepository.findByStatus(ConsultationStatus.PENDING)
                .stream()
                .map(consultationMapper::toConsultationResponse)
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 60000)
    public void cancelExpiredConsultations() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(3);
        List<Consultation> pending = consultationRepository.findByStatusAndCreatedAtBefore(ConsultationStatus.PENDING, cutoff);

        for (Consultation consultation : pending) {
            consultation.setStatus(ConsultationStatus.CANCELLED);
            consultationRepository.save(consultation);

            notificationService.sendNotification(
                    consultation.getPatientId(),
                    NotificationResponse.builder()
                            .chatId(consultation.getId())
                            .content("Consultation expired. No pharmacist responded.")
                            .type(NotificationType.SEEN)
                            .messageType(MessageType.TEXT)
                            .build());

        }
    }


}
