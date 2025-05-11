package com.drewsec.appointment_service.event.publisher;

import com.drewsec.commons.event.EventMessage;
import com.drewsec.commons.event.EventTopics;
import com.drewsec.commons.event.EventType;
import com.drewsec.commons.event.appointment.AppointmentCancelledEvent;
import com.drewsec.commons.event.appointment.AppointmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class KafkaAppointmentEventPublisher implements AppointmentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishCreated(AppointmentCreatedEvent event) {
        EventMessage<AppointmentCreatedEvent> message = EventMessage.<AppointmentCreatedEvent>builder()
                .type(EventType.CREATED)
                .payload(event)
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send(
                EventTopics.APPOINTMENT_CREATED,
                event.appointmentId().toString(),
                message
        );
    }

    @Override
    public void publishCancelled(AppointmentCancelledEvent event) {
        EventMessage<AppointmentCancelledEvent> message = EventMessage.<AppointmentCancelledEvent>builder()
                .type(EventType.CANCELLED)
                .payload(event)
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send(
                EventTopics.APPOINTMENT_CANCELLED,
                event.appointmentId().toString(),
                message
        );
    }

}