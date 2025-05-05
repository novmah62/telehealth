package com.drewsec.appointment_service.event.publisher;

import com.drewsec.appointment_service.event.model.AppointmentCancelledEvent;
import com.drewsec.appointment_service.event.model.AppointmentConfirmedEvent;
import com.drewsec.appointment_service.event.model.AppointmentCreatedEvent;
import com.drewsec.appointment_service.event.model.AppointmentUpdatedEvent;

public interface AppointmentEventPublisher {

    void publishCreated(AppointmentCreatedEvent event);
    void publishConfirmed(AppointmentConfirmedEvent event);
    void publishCancelled(AppointmentCancelledEvent event);
    void publishUpdated(AppointmentUpdatedEvent event);

}
