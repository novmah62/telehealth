package com.drewsec.consultation_service.service;

import com.drewsec.consultation_service.dto.response.NotificationResponse;

public interface NotificationService {

    void sendNotification(String userId, NotificationResponse notificationResponse);

}
