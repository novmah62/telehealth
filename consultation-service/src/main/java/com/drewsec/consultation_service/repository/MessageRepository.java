package com.drewsec.consultation_service.repository;

import com.drewsec.consultation_service.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByConsultationIdOrderByCreatedDateAsc(String consultationId);
    Page<Message> findByConsultationId(String consultationId, Pageable pageable);

}
