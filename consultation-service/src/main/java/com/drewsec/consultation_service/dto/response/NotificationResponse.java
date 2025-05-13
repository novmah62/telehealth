package com.drewsec.consultation_service.dto.response;

import com.drewsec.consultation_service.enumType.MessageType;
import com.drewsec.consultation_service.enumType.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private UUID chatId;
    private String content;
    private UUID senderId;
    private UUID receiverId;
    private MessageType messageType;
    private NotificationType type;
    private byte[] media;

}
