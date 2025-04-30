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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
public class Message {

    @Id
    private String id;
    private String consultationId;
    private String content;
    private MessageState state;
    private MessageType type;
    private String senderId;
    private String receiverId;
    private String mediaFilePath;
    private LocalDateTime createdDate;

}
