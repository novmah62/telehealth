
-- TELEHEALTH DATABASE SCHEMA (PostgreSQL)
-- Author: drewsec
-- Purpose: Professional schema for a Telehealth Prescription System (DDD + Microservices)

-- ====================
-- ENUMS
-- ====================
CREATE TYPE user_role AS ENUM ('doctor', 'pharmacist', 'patient', 'admin');
CREATE TYPE prescription_status AS ENUM ('issued', 'used', 'expired');
CREATE TYPE appointment_status AS ENUM ('scheduled', 'cancelled', 'completed');
CREATE TYPE audit_action AS ENUM ('CREATE', 'UPDATE', 'VERIFY', 'USE');

-- ====================
-- USERS CONTEXT
-- ====================
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role user_role NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- ====================
-- APPOINTMENT CONTEXT
-- ====================
CREATE TABLE appointments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    patient_id UUID NOT NULL REFERENCES users(id),
    doctor_id UUID NOT NULL REFERENCES users(id),
    scheduled_time TIMESTAMPTZ NOT NULL,
    status appointment_status DEFAULT 'scheduled',
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- ====================
-- CONSULTATION CONTEXT
-- ====================
CREATE TABLE consultations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    appointment_id UUID NOT NULL REFERENCES appointments(id),
    notes TEXT,
    video_call_url TEXT,
    started_at TIMESTAMPTZ,
    ended_at TIMESTAMPTZ
);

-- ====================
-- MEDICAL RECORD CONTEXT
-- ====================
CREATE TABLE medical_records (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    patient_id UUID NOT NULL REFERENCES users(id),
    doctor_id UUID NOT NULL REFERENCES users(id),
    consultation_id UUID REFERENCES consultations(id),
    diagnosis TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE record_versions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    record_id UUID NOT NULL REFERENCES medical_records(id),
    version_number INT NOT NULL,
    data JSONB NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- ====================
-- PRESCRIPTION CONTEXT
-- ====================
CREATE TABLE prescriptions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    patient_id UUID NOT NULL REFERENCES users(id),
    doctor_id UUID NOT NULL REFERENCES users(id),
    consultation_id UUID REFERENCES consultations(id),
    issued_at TIMESTAMPTZ DEFAULT NOW(),
    expires_at TIMESTAMPTZ,
    qr_code TEXT,
    signature TEXT,
    hash TEXT,
    status prescription_status DEFAULT 'issued'
);

CREATE TABLE prescription_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    prescription_id UUID NOT NULL REFERENCES prescriptions(id),
    medication_name VARCHAR(255) NOT NULL,
    dosage TEXT NOT NULL,
    instructions TEXT
);

-- ====================
-- VERIFICATION CONTEXT
-- ====================
CREATE TABLE verifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    prescription_id UUID NOT NULL REFERENCES prescriptions(id),
    verified_by UUID REFERENCES users(id),
    verified_at TIMESTAMPTZ DEFAULT NOW(),
    result BOOLEAN NOT NULL,
    reason TEXT
);

-- ====================
-- NOTIFICATION CONTEXT
-- ====================
CREATE TABLE notifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id),
    message TEXT NOT NULL,
    channel VARCHAR(50),
    sent_at TIMESTAMPTZ DEFAULT NOW()
);

-- -- ====================
-- -- BILLING CONTEXT
-- -- ====================
-- CREATE TABLE payments (
--     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--     user_id UUID NOT NULL REFERENCES users(id),
--     amount NUMERIC(10, 2),
--     method VARCHAR(50),
--     status VARCHAR(50),
--     paid_at TIMESTAMPTZ
-- );

-- ====================
-- AUDIT CONTEXT
-- ====================
CREATE TABLE audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    action audit_action NOT NULL,
    entity VARCHAR(255),
    entity_id UUID,
    performed_by UUID REFERENCES users(id),
    details JSONB,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- INDEXES for optimization
CREATE INDEX idx_prescriptions_patient ON prescriptions(patient_id);
CREATE INDEX idx_consultation_appointment ON consultations(appointment_id);
CREATE INDEX idx_medical_records_patient ON medical_records(patient_id);
CREATE INDEX idx_prescription_items_prescription ON prescription_items(prescription_id);
CREATE INDEX idx_audit_entity ON audit_logs(entity, entity_id);

-- ====================
-- END OF FILE
-- ====================
