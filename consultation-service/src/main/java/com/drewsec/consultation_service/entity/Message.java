package com.drewsec.consultation_service.entity;

import com.drewsec.consultation_service.enumType.MessageState;
import com.drewsec.consultation_service.enumType.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
public class Message {

    @Id
    private UUID id;
    private UUID consultationId;
    private String content;
    private MessageState state;
    private MessageType type;
    private UUID senderId;
    private UUID receiverId;
    private String mediaFilePath;
    private LocalDateTime createdDate;

}
