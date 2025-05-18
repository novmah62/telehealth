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

    // Appointment messages
    public static final String APPOINTMENT_BOOKED = "Appointment booked";
    public static final String APPOINTMENT_CANCELLED = "Appointment cancelled";
    public static final String DOCTOR_NEXT_APPOINTMENTS_FETCHED = "Doctor next appointments fetched";
    public static final String PATIENT_APPOINTMENTS_FETCHED = "Patient appointments fetched";

    // Slot messages
    public static final String SLOT_FETCHED = "Slot fetched successfully";
    public static final String FREE_SLOTS_FETCHED = "Free slots fetched successfully";
    public static final String SLOTS_BY_AVAILABILITY_FETCHED = "Slots by availability fetched successfully";
    public static final String BOOKED_SLOTS_COUNT_FETCHED = "Booked slots count per day fetched successfully";

    // Availability messages
    public static final String AVAILABILITY_CREATED = "Availability created successfully";
    public static final String DOCTOR_AVAILABILITIES_FETCHED = "Doctor availabilities fetched";
    public static final String AVAILABLE_WITH_SLOTS_FETCHED = "Available availabilities with slots fetched";

    // Examination messages
    public static final String EXAMINATION_CREATED = "Examination created successfully";
    public static final String EXAMINATION_FETCHED = "Examination fetched successfully";
    public static final String DOCTOR_EXAMINATIONS_FETCHED = "Doctor examinations fetched successfully";
    public static final String PATIENT_EXAMINATIONS_FETCHED = "Patient examinations fetched successfully";

    // Diagnosis messages
    public static final String DIAGNOSIS_ADDED = "Diagnosis added successfully";
    public static final String EXAMINATION_DIAGNOSES_FETCHED = "Examination diagnoses fetched successfully";

    // Symptom messages
    public static final String SYMPTOM_ADDED = "Symptom added successfully";
    public static final String EXAMINATION_SYMPTOMS_FETCHED = "Examination symptoms fetched successfully";

    // Clinical test messages
    public static final String CLINICAL_TEST_ORDERED = "Clinical test ordered successfully";
    public static final String CLINICAL_TEST_UPDATED = "Clinical test updated successfully";
    public static final String EXAMINATION_TESTS_FETCHED = "Examination tests fetched successfully";


}
