package com.drewsec.consultation_service.service;

import com.drewsec.consultation_service.dto.request.MessageRequest;
import com.drewsec.consultation_service.dto.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {

//    void sendMessage(MessageRequest request);
    void sendMessage(String senderId, MessageRequest request);
    void markMessageAsRead(String messageId);
//    void uploadMediaMessage(String consultationId, MultipartFile file);
    void uploadMediaMessage(String senderId, String consultationId, MultipartFile file);
    List<MessageResponse> getMessagesByConsultation(String consultationId);
    Page<MessageResponse> getMessagesByConsultation(String consultationId, Pageable pageable);

}
