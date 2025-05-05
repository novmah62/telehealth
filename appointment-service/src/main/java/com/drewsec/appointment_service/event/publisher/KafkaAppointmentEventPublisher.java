package com.drewsec.appointment_service.event.publisher;

import com.drewsec.appointment_service.event.model.AppointmentCancelledEvent;
import com.drewsec.appointment_service.event.model.AppointmentConfirmedEvent;
import com.drewsec.appointment_service.event.model.AppointmentCreatedEvent;
import com.drewsec.appointment_service.event.model.AppointmentUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaAppointmentEventPublisher implements AppointmentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_CREATED   = "appointment.created";
    private static final String TOPIC_CONFIRMED = "appointment.confirmed";
    private static final String TOPIC_CANCELLED = "appointment.cancelled";
    private static final String TOPIC_UPDATED   = "appointment.updated";

    @Override
    public void publishCreated(AppointmentCreatedEvent event) {
        kafkaTemplate.send(TOPIC_CREATED, event.getAppointmentId().toString(), event);
    }

    @Override
    public void publishConfirmed(AppointmentConfirmedEvent event) {
        kafkaTemplate.send(TOPIC_CONFIRMED, event.getAppointmentId().toString(), event);
    }

    @Override
    public void publishCancelled(AppointmentCancelledEvent event) {
        kafkaTemplate.send(TOPIC_CANCELLED, event.getAppointmentId().toString(), event);
    }

    @Override
    public void publishUpdated(AppointmentUpdatedEvent event) {
        kafkaTemplate.send(TOPIC_UPDATED, event.getAppointmentId().toString(), event);
    }
}