package com.drewsec.consultation_service.service.impl;

import com.drewsec.consultation_service.dto.response.NotificationResponse;
import com.drewsec.consultation_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotification(UUID userId, NotificationResponse notification) {
        log.info("Sending WS notification to {} with payload {}", userId, notification);
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/chat",
                notification
        );
    }

}
