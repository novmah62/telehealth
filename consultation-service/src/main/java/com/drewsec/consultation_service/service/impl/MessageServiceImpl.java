package com.drewsec.consultation_service.service.impl;

import com.drewsec.commons.exception.ResourceNotFoundException;
import com.drewsec.consultation_service.dto.request.MessageRequest;
import com.drewsec.consultation_service.dto.response.MessageResponse;
import com.drewsec.consultation_service.dto.response.NotificationResponse;
import com.drewsec.consultation_service.entity.Consultation;
import com.drewsec.consultation_service.entity.Message;
import com.drewsec.consultation_service.enumType.MessageState;
import com.drewsec.consultation_service.enumType.MessageType;
import com.drewsec.consultation_service.enumType.NotificationType;
import com.drewsec.consultation_service.mapper.MessageMapper;
import com.drewsec.consultation_service.repository.ConsultationRepository;
import com.drewsec.consultation_service.repository.MessageRepository;
import com.drewsec.consultation_service.service.FileService;
import com.drewsec.consultation_service.service.MessageService;
import com.drewsec.consultation_service.service.NotificationService;
import com.drewsec.consultation_service.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final ConsultationRepository consultationRepository;
    private final FileService fileService;
    private final NotificationService notificationService;

    @Override
    public void sendMessage(UUID senderId, MessageRequest request) {
        Message message = new Message();
        message.setConsultationId(request.getConsultationId());
        message.setContent(request.getContent());
        message.setSenderId(senderId);
        message.setReceiverId(request.getReceiverId());
        message.setState(MessageState.SENT);
        message.setType(request.getType());
        message.setCreatedDate(LocalDateTime.now());

        messageRepository.save(message);

        notificationService.sendNotification(
                request.getReceiverId(),
                NotificationResponse.builder()
                        .chatId(request.getConsultationId())
                        .content(request.getContent())
                        .senderId(senderId)
                        .receiverId(request.getReceiverId())
                        .messageType(request.getType())
                        .type(NotificationType.MESSAGE)
                        .build()
        );
    }

    @Override
    public void markMessageAsRead(UUID messageId) {
        messageRepository.findById(messageId).ifPresent(msg -> {
            msg.setState(MessageState.SEEN);
            messageRepository.save(msg);
        });
    }

    @Override
    public void uploadMediaMessage(UUID senderId, UUID consultationId, MultipartFile file) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "consultation ID", consultationId.toString()));

        UUID receiverId = consultation.getConsultantId().equals(senderId)
                ? consultation.getPatientId()
                : consultation.getConsultantId();

        String filePath = fileService.saveFile(file, senderId);

        Message message = new Message();
        message.setConsultationId(consultationId);
        message.setMediaFilePath(filePath);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setState(MessageState.SENT);
        message.setType(MessageType.IMAGE);
        message.setCreatedDate(LocalDateTime.now());

        messageRepository.save(message);

        notificationService.sendNotification(
                receiverId,
                NotificationResponse.builder()
                        .chatId(consultationId)
                        .media(FileUtil.readFileFromLocation(filePath))
                        .senderId(senderId)
                        .receiverId(receiverId)
                        .messageType(MessageType.IMAGE)
                        .type(NotificationType.IMAGE)
                        .build()
        );
    }

    @Override
    public List<MessageResponse> getMessagesByConsultation(UUID consultationId) {
        return messageRepository.findByConsultationIdOrderByCreatedDateAsc(consultationId)
                .stream().map(messageMapper::toMessageResponse).toList();
    }

    @Override
    public Page<MessageResponse> getMessagesByConsultation(UUID consultationId, Pageable pageable) {
        return messageRepository.findByConsultationId(consultationId, pageable)
                .map(messageMapper::toMessageResponse);
    }

}
