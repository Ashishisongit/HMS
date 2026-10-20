
USE hospital_management;
-- Adding the Data to Doctor Table 
INSERT INTO doctors (first_name, last_name, specialization, phone, consultation_fee, max_daily_capacity, availability_status, account_status, email, years_of_experience) VALUES
('Arjun', 'Sharma', 'Cardiology', '9876543210', 1200.00, 15, 'Available', 'Active', 'dr.arjun.sharma@gmail.com', 16),
('Priya', 'Nair', 'Pediatrics', '9812345678', 800.00, 20, 'Available', 'Active', 'priya.nair@outlook.com', 9),
('Amit', 'Patel', 'Dermatology', '9123456789', 1000.00, 12, 'Available', 'Active', 'amitpatel.md@yahoo.com', 11),
('Ananya', 'Iyer', 'Neurology', '9567812345', 2500.00, 8, 'On Leave', 'Active', 'ananya.iyer@health.in', 22),
('Rajesh', 'Kumar', 'Orthopedics', '9445566778', 1500.00, 14, 'Available', 'Active', 'dr.rajeshkumar@gmail.com', 14),
('Sunita', 'Deshmukh', 'General Medicine', '9321098765', 500.00, 25, 'Available', 'Active', NULL, 5),
('Vikram', 'Singh', 'Psychiatry', '9765432109', 1800.00, 6, 'Available', 'Active', 'vsingh.psych@gmail.com', 13),
('Meera', 'Reddy', 'Gynecology', '9988776655', 1200.00, 18, 'Available', 'Suspended', 'meera.reddy@apollo.in', 10),
('Sanjay', 'Gupta', 'Ophthalmology', '9654321098', 700.00, 15, 'Available', 'Active', 'sanjay.gupta@clinic.net', 20),
('Deepa', 'Choudhury', 'Oncology', '9012345678', 3000.00, 5, 'Available', 'Active', NULL, 18);

-- Adding the Data to Patient Table 
INSERT INTO patients (patient_id,first_name, last_name, date_of_birth, gender, phone, email, address, account_status, emergency_contact_phone) VALUES
(5,'Rohan', 'Sharma', '1990-05-14', 'Male', '9876543210', 'rohan.sharma@gmail.com', 'Mumbai', 'Active', '+91 9876543211'),
(6,'Aarav', 'Patel', '1985-11-23', 'Male', '9123456789', 'aarav.patel@yahoo.com', 'Ahmedabad', 'Active', NULL),
(7,'Diya', 'Nair', '1998-08-02', 'Female', '9812345678', 'diya.nair@outlook.com', 'Bengaluru', 'Active', '+91 9812345670'),
(8,'Sai', 'Reddy', NULL, 'Male', '9988776655', 'sai.reddy@gmail.com', 'Hyderabad', 'Active', '+91 9988776650'),
(9,'Ananya', 'Iyer', '2002-01-15', 'Female', '9567812345', NULL, 'Chennai', 'Active', '+91 9567812340'),
(10,'Kabir', 'Singh', '1975-04-30', 'Male', '9765432109', 'kabir.s@gmail.com', 'Delhi', 'Inactive', '+91 9765432100'),
(11,'Meera', 'Joshi', '1993-12-12', 'Female', '9654321098', 'meera.joshi@gmail.com', 'Pune', 'Active', NULL),
(12,'Rahul', 'Das', '1988-07-19', 'Male', '9012345678', NULL, 'Kolkata', 'Active', '+91 9012345670'),
(13,'Pooja', 'Mishra', NULL, 'Female', '9321098765', 'pooja.m@gmail.com', 'Lucknow', 'Active', '+91 9321098760'),
(14,'Aditya', 'Verma', '1995-09-05', 'Male', '9445566778', 'aditya.v@gmail.com', 'Jaipur', 'Suspended', '+91 9445566770');

INSERT INTO appointments (
    patient_id, doctor_id, scheduled_start, scheduled_end, status, reason, fee_charged
) VALUES
-- October 2026 Appointments
(1, 1, '2026-10-01 09:00:00', '2026-10-01 09:30:00', 'Scheduled', 'Routine Checkup', 0.00),
(2, 2, '2026-10-02 10:00:00', '2026-10-02 10:45:00', 'Scheduled', 'Follow-up', 0.00),
(3, 3, '2026-10-05 11:15:00', '2026-10-05 12:00:00', 'Scheduled', 'Consultation', 0.00),
(4, 4, '2026-10-06 14:00:00', '2026-10-06 14:30:00', 'Scheduled', 'Dental Clean', 0.00),
(5, 5, '2026-10-08 15:00:00', '2026-10-08 16:00:00', 'Scheduled', 'Eye Exam', 0.00),
(6, 6, '2026-10-12 08:30:00', '2026-10-12 09:00:00', 'Scheduled', 'Skin Rash Check', 0.00),
(7, 7, '2026-10-14 13:00:00', '2026-10-14 13:30:00', 'Scheduled', 'Therapy Session', 0.00),
(8, 8, '2026-10-15 10:30:00', '2026-10-15 11:00:00', 'Scheduled', 'Blood Work Review', 0.00),
(9, 9, '2026-10-19 16:15:00', '2026-10-19 17:00:00', 'Scheduled', 'Heart Screening', 0.00),
(10, 10, '2026-10-21 09:45:00', '2026-10-21 10:15:00', 'Scheduled', 'General Checkup', 0.00),
(11, 11, '2026-10-22 11:00:00', '2026-10-22 11:45:00', 'Scheduled', 'Joint Pain Evaluation', 0.00),
(12, 12, '2026-10-26 14:30:00', '2026-10-26 15:00:00', 'Scheduled', 'Prescription Renewal', 0.00),
(13, 13, '2026-10-28 10:00:00', '2026-10-28 10:30:00', 'Scheduled', 'Ear Pain Check', 0.00),
(14, 1, '2026-10-29 15:30:00', '2026-10-29 16:00:00', 'Scheduled', 'Follow-up', 0.00),

-- November 2026 Appointments
(1, 2, '2026-11-02 09:00:00', '2026-11-02 09:30:00', 'Scheduled', 'Routine Checkup', 0.00),
(2, 3, '2026-11-04 10:00:00', '2026-11-04 10:30:00', 'Scheduled', 'Follow-up', 0.00),
(3, 4, '2026-11-06 11:00:00', '2026-11-06 11:30:00', 'Scheduled', 'Consultation', 0.00),
(4, 5, '2026-11-09 14:00:00', '2026-11-09 14:30:00', 'Scheduled', 'Vaccination', 0.00),
(5, 6, '2026-11-11 15:00:00', '2026-11-11 15:30:00', 'Scheduled', 'Follow-up', 0.00),
(6, 7, '2026-11-13 08:30:00', '2026-11-13 09:00:00', 'Scheduled', 'Therapy Session', 0.00),
(7, 8, '2026-11-16 13:00:00', '2026-11-16 13:30:00', 'Scheduled', 'Follow-up Check', 0.00),
(8, 9, '2026-11-18 10:30:00', '2026-11-18 11:00:00', 'Scheduled', 'Cardio Review', 0.00),
(9, 10, '2026-11-20 16:00:00', '2026-11-20 16:30:00', 'Scheduled', 'General Consultation', 0.00),
(10, 11, '2026-11-23 09:45:00', '2026-11-23 10:15:00', 'Scheduled', 'Follow-up', 0.00),
(11, 12, '2026-11-25 11:00:00', '2026-11-25 11:30:00', 'Scheduled', 'Physical Exam', 0.00);


DESC patients;
DESC appointments;
SELECT * FROM patients;
SELECT * FROM appointments;
DELETE FROM patients WHERE patient_id BETWEEN 25 AND 34;
DELETE FROM patients WHERE patient_id BETWEEN 5 AND 14;
SELECT * FROM doctors;