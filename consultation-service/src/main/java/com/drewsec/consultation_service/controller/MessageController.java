package com.drewsec.consultation_service.controller;

import com.drewsec.commons.dto.ApiResponse;
import com.drewsec.consultation_service.dto.request.MessageRequest;
import com.drewsec.consultation_service.dto.response.MessageResponse;
import com.drewsec.consultation_service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ApiResponse<Void> sendMessage(@RequestBody MessageRequest request) {
        messageService.sendMessage(request);
        return new ApiResponse<>(STATUS_OK, MESSAGE_SENT);
    }

    @PostMapping("/read/{messageId}")
    public ApiResponse<Void> markAsRead(@PathVariable String messageId) {
        messageService.markMessageAsRead(messageId);
        return new ApiResponse<>(STATUS_OK, MESSAGE_MARKED_READ);
    }

    @PostMapping("/upload/{consultationId}")
    public ApiResponse<Void> uploadMedia(
            @PathVariable String consultationId,
            @RequestParam("file") MultipartFile file) {
        messageService.uploadMediaMessage(consultationId, file);
        return new ApiResponse<>(STATUS_OK, MEDIA_UPLOADED);
    }

    @GetMapping("/consultation/{consultationId}")
    public ApiResponse<List<MessageResponse>> getMessages(
            @PathVariable String consultationId) {
        List<MessageResponse> msg = messageService.getMessagesByConsultation(consultationId);
        return new ApiResponse<>(STATUS_OK, MESSAGES_FETCHED, msg);
    }

    @GetMapping("/consultation/{consultationId}/page")
    public ApiResponse<Page<MessageResponse>> getMessagesPaged(
            @PathVariable String consultationId,
            Pageable pageable) {
        Page<MessageResponse> page = messageService.getMessagesByConsultation(consultationId, pageable);
        return new ApiResponse<>(STATUS_OK, MESSAGES_FETCHED_PAGED, page);
    }

}
