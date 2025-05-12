package com.drewsec.appointment_service.event.publisher;


import com.drewsec.commons.event.appointment.AppointmentCancelledEvent;
import com.drewsec.commons.event.appointment.AppointmentCreatedEvent;

public interface AppointmentEventPublisher {

    void publishCreated(AppointmentCreatedEvent event);
    void publishCancelled(AppointmentCancelledEvent event);

}
