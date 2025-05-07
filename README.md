# Professional Report: Secure Telehealth E-Prescription System

## 1. Introduction

The digital transformation in healthcare is ushering in a new era that facilitates patient access to specialists and alleviates the burden on traditional medical facilities. Among these innovations, Telehealth systems not only enable remote consultations but also address the complex challenges of issuing and managing electronic prescriptions — which can be susceptible to forgery, unauthorized alterations, and misuse of sensitive medical information. This report presents a comprehensive solution based on a microservices architecture, event-driven design, and DevSecOps principles, incorporating an Edge layer, API Gateway, specialized functional services, strong authentication, digital signatures, secure QR codes (SQRC), immutable audit trails, and full compliance with Circular 04/2022/TT-BYT. The goal is to ensure that every e-prescription is issued securely, transparently, traceable, and fully lawful.


## 2. System Objectives

The system targets five strategic objectives throughout the consultation, prescription, and medication delivery process:

- **Authorized Issuance:** Only credentialed physicians may create prescriptions, eliminating the risk of unauthorized orders.

- **Digital Signature Integrity:** Each prescription is sealed with an X.509 digital signature to guarantee content integrity and authenticity, preventing any tampering or forgery.

- **Secure QR Code (SQRC):** SQRC enables pharmacies to verify an e-prescription’s status ("issued" or "used") without exposing its contents.

- **Immutable Audit Trail:** A tamper-proof audit log captures every action in real time, supporting comprehensive auditing, regulatory inspection, and incident investigation.

- **Regulatory Compliance:** The system fully adheres to the provisions of Circular 04/2022/TT-BYT and associated directives concerning QR code format and data retrieval.


## 3. System Architecture

<p align="center">
  <img src="document/diagrams/telehealth-v2-global-architecture.drawio.png" alt="Architecture" width="1727">
</p>

### 3.1 Edge Layer and API Gateway

All incoming user requests are initially handled at the Edge layer via Spring Cloud Gateway or Kong. Here, the Gateway performs OAuth2/OpenID Connect authentication through Keycloak, enforces mandatory multi-factor authentication (MFA) for physicians, issues JWT tokens with role and scope claims, and applies rate-limiting and basic Web Application Firewall (WAF) protections to guard against API-based attacks.

### 3.2 Core Microservices

The system comprises nine primary microservices, communicating through Apache Kafka to ensure an event-driven design and horizontal scalability:

| **Service**                | **Description** |
|----------------------------|-----------------|
| **Consultation Service**   | Facilitates preliminary consultations through WebSocket-based chat for pharmacist-led triage. Logs all interactions without interfering in detailed clinical examinations. |
| **Appointment Service**    | Manages scheduling and cancellation of appointments, emits `appointment.created` and `appointment.cancelled` events, and maintains appointment history. |
| **Examination Service**    | Records patient symptoms, test results, and final diagnoses to inform prescription generation. |
| **Prescription Service**   | Issues e-prescriptions. Only authorized physicians may perform CRUD operations. Generates codes compliant with Circular 04/2022/TT-BYT, digitally signs documents, renders SQRC, and updates prescription status. |
| **Medical Records Service**| Aggregates data from Consultation, Examination, Prescription, and Appointment services. Supports versioning and tightly controlled query APIs. |
| **Billing Service**        | Processes payments (e.g., Stripe, PayPal), issues invoices, and emits `payment.completed` events. |
| **Notification Service**   | Sends email, SMS, and push notifications. Integrates with a scheduler and Kafka to dispatch appointment reminders and alerts. |
| **Analytics Service**      | Executes analytical queries and generates KPI reports. |
| **Audit Log Service**      | Captures immutable logs with timestamps, actor IDs, and IP addresses for traceable auditing. |

[//]: # (- **Consultation Service:** Facilitates preliminary consultations through WebSocket-based chat for pharmacist-led triage, logging all interactions without interfering in detailed clinical examinations.)

[//]: # ()
[//]: # (- **Appointment Service:** Manages scheduling and cancellation of appointments, issues appointment.created and appointment.cancelled events, and maintains a complete history.)

[//]: # ()
[//]: # (- **Examination Service:** Records patient symptoms, test results, and final diagnoses to inform prescription generation.)

[//]: # ()
[//]: # (- **Prescription Service:** Responsible for issuing e-prescriptions; only authorized physicians may perform CRUD operations. The service automatically generates codes compliant with Circular 04/2022/TT-BYT, signs the document with X.509, applies a sign-then-encrypt process to render the SQRC, verifies internally, and updates the status from "issued" to "used".)

[//]: # ()
[//]: # (- **Medical Records Service:** Aggregates records from Consultation, Diagnosis, Prescription, and Appointment services, supports versioning, and provides tightly controlled query APIs.)

[//]: # ()
[//]: # (- **Billing Service:** Processes payments via Stripe or PayPal, issues electronic invoices, and emits payment.completed events.)

[//]: # ()
[//]: # (- **Notification Service:** Enqueues email, SMS, and push notifications, integrates with a scheduler and Kafka to send appointment reminders, test result alerts, and prescription notifications.)

[//]: # ()
[//]: # (- **Analytics Service:** Handles complex analytical queries and KPI reporting.)

[//]: # ()
[//]: # (- **Audit Log Service:** Captures immutable logs of all events with timestamps, actor IDs, and IP addresses, ensuring a comprehensive audit trail.)

### 3.3 Data Storage

The storage infrastructure is organized into three tiers:

- **PostgreSQL:** Serves the five core services (Appointment, Examination, Billing, Medical Records, Prescription) with ACID guarantees and support for complex relational queries.

- **MongoDB:** Hosts document-oriented data for the remaining services, providing schema flexibility.

- **Elasticsearch:** Functions as an indexing layer for advanced analytics and log searches, synchronized via scheduled replication or Debezium Change Data Capture (CDC).

### 3.4 Event Bus

Apache Kafka serves as the backbone of the event-driven architecture, ensuring reliable, real-time communication between system components

### 3.5 Observability

OpenTelemetry collects metrics and distributed traces. Prometheus, Grafana, and Loki handle metrics, dashboards, and log monitoring, respectively.

### 3.6 Saga Choreography

The system employs a decentralized Saga pattern using Kafka events for service coordination. For example, issuing a prescription triggers a sequence of events across Notification, Medical Records, and Audit services. In case of failures, a compensating event (prescription.revoked) ensures consistent rollback behavior.


## 4. E-Prescription Security Details

### 4.1 Authentication &amp; Authorization

Keycloak provides OAuth2/OpenID Connect authentication with mandatory MFA for physicians. JWT tokens include embedded role and scope claims, all validated at the Edge Gateway.

### 4.2 X.509 Digital Signatures

Certificates are issued by a health authority, with private keys stored in a Hardware Security Module (HSM) and public keys distributed within the network. The Prescription Service applies a digital signature to each document (PDF/JSON) prior to encryption.

### 4.3 Secure QR Code (SQRC)

A sign-then-encrypt model is used: the payload (Prescription ID, SHA-256 hash, and digital signature) is encrypted; the QR code displays only the ciphertext block. Pharmacies must call the Prescription Service to decrypt and verify the content.

### 4.4 Verification Endpoint

External systems or pharmacists can invoke GET /api/prescriptions/verify/{prescriptionId} to retrieve a JSON response containing valid, status (issued | used | expired), physician details, issuedAt, and expiresAt fields.

### 4.5 Audit Trail &amp; Versioning

Every operation (create, update, verify, use) publishes an event to Kafka and is recorded immutably. Each update generates a new version record with a timestamp, actor ID, and IP address to support end-to-end traceability.


## 5. Infrastructure & CI/CD Pipeline

<p align="center">
  <img src="document/diagrams/telehealth-pipeline-architecture.drawio.png" alt="Architecture" width="1727">
</p>

### 5.1 Infrastructure Overview

The solution is deployed across five Ubuntu servers: four on-premises VMs (GitLab server, build server, development server, and database server) and one Amazon EC2 instance hosting a private Docker Registry.

### 5.2 GitLab CI/CD Pipeline Stages

Every code push triggers a six-stage GitLab CI/CD pipeline, running on dedicated runners:

| Stage                     | Description                                                                                         |
|---------------------------|-----------------------------------------------------------------------------------------------------|
| **Build**                 | The build server executes `mvn clean package` and builds Docker images tagged by commit SHA.       |
| **Test Source Code**      | Static Application Security Testing (SAST) via SonarQube, unit tests (JUnit/Mockito) và integration tests (embedded H2/PostgreSQL). SonarQube enforces quality/security gates. |
| **Push**                  | Push built Docker images to the private Harbor registry (EC2). Pipeline aborts if push fails.      |
| **Security Scan Image**   | Trivy scans each container image for HIGH/CRITICAL CVEs; pipeline fails on any blocking vulnerability. |
| **Deploy**                | Pull new images onto the development server via SSH and run `docker-compose down && docker-compose up -d`. |
| **Security Scan Website** | DAST with Arachni against the staging URL, reporting XSS, CSRF, IDOR…                                 |
| **Performance Testing**   | k6 simulates load (e.g. 50 virtual users for 2 minutes); fails if p95 latency > 300 ms or error rate > 3 %. |

Logs, metrics, test reports, and scan artifacts are centrally stored for traceability and auditing.

## 6. Business Workflow

### 6.1 Registration &amp; Initial Consultation

Patients register an account on the Telehealth platform by providing personal information and verifying via email or SMS. Upon activation, they may initiate a pharmacist-led pre-consultation through the Consultation Service to triage symptoms and determine next steps.

### 6.2 Appointment Booking

For detailed clinical consultations, patients use the Appointment Service to schedule an appointment with a physician. The system validates patient credentials, verifies insurance coverage if applicable, and sends confirmation notifications via email, SMS, or push messages.

### 6.3 Clinical Examination &amp; Diagnosis

Physicians conduct in-person examinations at a healthcare facility to observe symptoms, perform physical checks, and order necessary tests. All collected data — including symptoms, test results, and final diagnosis — is recorded in the Examination Service, which emits corresponding Kafka events for downstream processing.

### 6.4 Prescription Issuance

Based on the diagnosis, the physician issues an e-prescription via the Prescription Service. The system automatically generates a compliant code, applies digital signatures, encrypts the payload, and renders the SQRC.

### 6.5 Medical Record Update

Immediately after issuance, the Medical Records Service aggregates data from Consultation, Diagnosis, Prescription, and Appointment services to update the patient’s electronic health record, preserving version history for all changes.

### 6.6 Payment &amp; Insurance Processing

Patients complete payment using the Billing Service, which integrates with Stripe or PayPal and verifies insurance claims through third-party APIs. Successful transactions emit payment.completed events, automatically updating records in the Medical Records Service.


## 7. Benefits

This solution delivers four key advantages:

- **Absolute Security:** Digital signatures and SQRC eliminate the risk of prescription forgery or tampering.

- **Complete Transparency:** A fully immutable audit trail ensures end-to-end visibility and traceability, facilitating audits and regulatory inspections.

- **Regulatory Compliance:** The system rigorously adheres to health ministry guidelines on prescription formatting, data retention, and expiration.

- **Scalable &amp; Agile Architecture:** Microservices, event-driven design, and DevSecOps practices enable rapid deployment, seamless scalability, and straightforward third-party integrations.
