package com.drewsec.consultation_service.mapper;

import com.drewsec.consultation_service.dto.response.MessageResponse;
import com.drewsec.consultation_service.entity.Message;
import com.drewsec.consultation_service.util.FileUtil;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .type(message.getType())
                .state(message.getState())
                .createdAt(message.getCreatedDate())
                .media(FileUtil.readFileFromLocation(message.getMediaFilePath()))
                .build();
    }

}
