package com.drewsec.consultation_service.service;

import com.drewsec.consultation_service.dto.response.NotificationResponse;

import java.util.UUID;

public interface NotificationService {

    void sendNotification(UUID userId, NotificationResponse notificationResponse);

}
