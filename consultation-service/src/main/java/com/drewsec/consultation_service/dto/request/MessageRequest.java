package com.drewsec.consultation_service.dto.request;

import com.drewsec.consultation_service.enumType.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private UUID receiverId;
    private String content;
    private MessageType type;
    private UUID consultationId;

}
