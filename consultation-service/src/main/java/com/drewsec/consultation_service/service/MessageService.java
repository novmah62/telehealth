package com.drewsec.consultation_service.service;

import com.drewsec.consultation_service.dto.request.MessageRequest;
import com.drewsec.consultation_service.dto.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    void sendMessage(UUID senderId, MessageRequest request);
    void markMessageAsRead(UUID messageId);
    void uploadMediaMessage(UUID senderId, UUID consultationId, MultipartFile file);
    List<MessageResponse> getMessagesByConsultation(UUID consultationId);
    Page<MessageResponse> getMessagesByConsultation(UUID consultationId, Pageable pageable);

}
