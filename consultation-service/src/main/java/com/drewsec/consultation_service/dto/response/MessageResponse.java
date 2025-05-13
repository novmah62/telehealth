package com.drewsec.consultation_service.dto.response;

import com.drewsec.consultation_service.enumType.MessageState;
import com.drewsec.consultation_service.enumType.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private UUID id;
    private String content;
    private MessageType type;
    private MessageState state;
    private UUID senderId;
    private UUID receiverId;
    private LocalDateTime createdAt;
    private byte[] media;

}
