CREATE DATABASE Hospital_Management;
USE Hospital_Management;



-- 1. PATIENT TABLE
CREATE TABLE IF NOT EXISTS patients (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(15),
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255),
    account_status VARCHAR(20) NOT NULL DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

USE hospital_management;

ALTER TABLE patients
ADD COLUMN emergency_contact_name VARCHAR(100),
ADD COLUMN emergency_contact_phone VARCHAR(15),
ADD COLUMN updated_at TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP;

-- 2. DOCTOR TABLE
CREATE TABLE IF NOT EXISTS doctors (
    doctor_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    consultation_fee DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    max_daily_capacity INT NOT NULL DEFAULT 10,
    availability_status VARCHAR(20) NOT NULL DEFAULT 'Available',
    account_status VARCHAR(20) NOT NULL DEFAULT 'Active',
    CONSTRAINT chk_doctor_fee
        CHECK (consultation_fee >= 0),
    CONSTRAINT chk_doctor_capacity
        CHECK (max_daily_capacity > 0)
);



ALTER TABLE doctors
ADD COLUMN email VARCHAR(100),
ADD COLUMN years_of_experience INT NOT NULL DEFAULT 0,
ADD COLUMN room_number VARCHAR(10);

-- 3. APPOINTMENT TABLE
CREATE TABLE IF NOT EXISTS appointments (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    scheduled_start DATETIME NOT NULL,
    scheduled_end DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Scheduled',
    reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_appointment_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients(patient_id),
    CONSTRAINT fk_appointment_doctor
        FOREIGN KEY (doctor_id)
        REFERENCES doctors(doctor_id),
    CONSTRAINT chk_appointment_time
        CHECK (scheduled_end > scheduled_start),
    CONSTRAINT chk_appointment_status
        CHECK (status IN ('Scheduled', 'Completed', 'Cancelled')
    )
);

ALTER TABLE appointments
ADD COLUMN consultation_notes VARCHAR(500),
ADD COLUMN cancellation_reason VARCHAR(255),
ADD COLUMN fee_charged DECIMAL(10,2) NOT NULL DEFAULT 0.00;


CREATE INDEX idx_patients_phone
ON patients(phone);

CREATE INDEX idx_doctors_specialization
ON doctors(specialization);

CREATE INDEX idx_appointments_doctor_start
ON appointments(doctor_id, scheduled_start);

CREATE INDEX idx_appointments_patient_start
ON appointments(patient_id, scheduled_start);
