-- =============================
-- Create databases
-- =============================
CREATE DATABASE user_service;
CREATE DATABASE appointment_service;
CREATE DATABASE examination_service;
CREATE DATABASE prescription_service;

-- -- =============================
-- -- Create users & grant privileges for user_service
-- -- =============================
-- CREATE USER user_svc_user WITH PASSWORD 'user_svc_pass';
-- GRANT ALL PRIVILEGES ON DATABASE user_service TO user_svc_user;
--
-- \connect appointment_service
-- CREATE USER appointment_svc_user WITH PASSWORD 'appointment_svc_pass';
-- GRANT ALL PRIVILEGES ON DATABASE appointment_service TO appointment_svc_user;
--
-- \connect prescription_service
-- CREATE USER prescription_svc_user WITH PASSWORD 'prescription_svc_pass';
-- GRANT ALL PRIVILEGES ON DATABASE prescription_service TO prescription_svc_user;
--
-- \connect verification_service
-- CREATE USER verification_svc_user WITH PASSWORD 'verification_svc_pass';
-- GRANT ALL PRIVILEGES ON DATABASE verification_service TO verification_svc_user;
