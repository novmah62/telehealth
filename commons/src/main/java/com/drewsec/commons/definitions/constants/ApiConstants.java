package com.drewsec.commons.definitions.constants;

public class ApiConstants {

    private ApiConstants() {}

    // Status codes
    public static final int STATUS_OK = 200;
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_INTERNAL_ERROR = 500;

    // Consultation messages
    public static final String CONSULTATION_CREATED = "Consultation request created";
    public static final String CONSULTATION_ACCEPTED = "Consultation accepted";
    public static final String CONSULTATION_COMPLETED = "Consultation completed";
    public static final String CONSULTATION_ALREADY_CLOSED = "Consultation already closed or invalid status";
    public static final String INVALID_CONSULTATION_ACCESS = "You are not part of this consultation";

    public static final String PATIENT_CONSULTATIONS_FETCHED = "Patient consultations fetched";
    public static final String CONSULTANT_CONSULTATIONS_FETCHED = "Consultant consultations fetched";
    public static final String PENDING_CONSULTATIONS_FETCHED = "Pending consultations fetched";

    // Message messages
    public static final String MESSAGE_SENT = "Message sent";
    public static final String MESSAGE_MARKED_READ = "Message marked as read";
    public static final String MEDIA_UPLOADED = "Media uploaded and message sent";
    public static final String MESSAGES_FETCHED = "Messages fetched";
    public static final String MESSAGES_FETCHED_PAGED = "Messages fetched paged";

}
