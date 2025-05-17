package com.drewsec.consultation_service.repository;

import com.drewsec.consultation_service.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends MongoRepository<Message, UUID> {

    List<Message> findByConsultationIdOrderByCreatedDateAsc(UUID consultationId);
    Page<Message> findByConsultationId(UUID consultationId, Pageable pageable);

}
