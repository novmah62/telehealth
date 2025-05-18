package com.drewsec.commons.event;

public interface EventTopics {
    String APPOINTMENT_CREATED   = "appointment.created";
    String APPOINTMENT_CANCELLED = "appointment.cancelled";
    String EXAMINATION_CREATED = "examination.created";
    String EXAMINATION_COMPLETED = "examination.completed";
    String EXAMINATION_CANCELLED = "examination.cancelled";
    String EXAMINATION_COMPENSATED = "examination.compensated";
    String PRESCRIPTION_CREATED = "prescription.created";
    String PRESCRIPTION_COMPENSATED = "prescription.compensated";
}
