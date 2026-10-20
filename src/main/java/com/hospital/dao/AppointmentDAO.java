package com.hospital.dao;

import com.hospital.entity.Appointment;
import com.hospital.entity.Patient;
import com.hospital.entity.Doctor;
import com.hospital.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDAO {

    // Book an appointment
    public void bookAppointment(
            int patientId,
            int doctorId,
            LocalDateTime start,
            LocalDateTime end,
            String reason) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            // Find patient and doctor
            Patient patient = session.get(Patient.class, patientId);
            Doctor doctor = session.get(Doctor.class, doctorId);

            if (patient == null) {
                System.out.println("Patient not found!");
                transaction.rollback();
                return;
            }

            if (doctor == null) {
                System.out.println("Doctor not found!");
                transaction.rollback();
                return;
            }

            // Check patient account status
            if (!"Active".equalsIgnoreCase(
                    patient.getAccountStatus())) {

                System.out.println("Patient account is not active!");
                transaction.rollback();
                return;
            }

            // Check doctor account and availability
            if (!"Active".equalsIgnoreCase(
                    doctor.getAccountStatus())
                    || !"Available".equalsIgnoreCase(
                    doctor.getAvailabilityStatus())) {

                System.out.println("Doctor is not available!");
                transaction.rollback();
                return;
            }

            // Validate appointment time
            if (start == null || end == null || !end.isAfter(start)) {
                System.out.println("Invalid appointment time!");
                transaction.rollback();
                return;
            }

            // Check for overlapping appointments with this doctor
            Long conflicts = session.createQuery(
                    "SELECT COUNT(a) FROM Appointment a " +
                    "WHERE a.doctor.doctorId = :doctorId " +
                    "AND a.status = 'Scheduled' " +
                    "AND a.scheduledStart < :end " +
                    "AND a.scheduledEnd > :start",
                    Long.class)
                    .setParameter("doctorId", doctorId)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getSingleResult();

            if (conflicts > 0) {
                System.out.println(
                        "Doctor already has an appointment during this time!");
                transaction.rollback();
                return;
            }

            // Create and save appointment
            Appointment appointment = new Appointment(
                    patient, doctor, start, end, reason);

            session.persist(appointment);
            transaction.commit();

            System.out.println(
                    "Appointment booked successfully! Appointment ID: "
                    + appointment.getAppointmentId());

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Error booking appointment: " + e.getMessage());
        }
    }

    // View all appointments
    public void viewAllAppointments() {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            List<Appointment> appointments =
                    session.createQuery(
                            "FROM Appointment ORDER BY scheduledStart",
                            Appointment.class)
                            .getResultList();

            if (appointments.isEmpty()) {
                System.out.println("No appointments found.");
                return;
            }

            for (Appointment a : appointments) {
                System.out.println(
                        "Appointment ID: " + a.getAppointmentId()
                        + " | Patient: "
                        + a.getPatient().getFirstName()
                        + " " + a.getPatient().getLastName()
                        + " | Doctor: "
                        + a.getDoctor().getFirstName()
                        + " " + a.getDoctor().getLastName()
                        + " | Start: " + a.getScheduledStart()
                        + " | End: " + a.getScheduledEnd()
                        + " | Status: " + a.getStatus());
            }

        } catch (Exception e) {
            System.out.println(
                    "Error viewing appointments: " + e.getMessage());
        }
    }

    // View appointments for a doctor on a specific date
public void viewDoctorSchedule(int doctorId,
                               java.time.LocalDate date) {

    try (Session session =
                 HibernateUtil.getSessionFactory().openSession()) {

        java.time.LocalDateTime startOfDay = date.atStartOfDay();
        java.time.LocalDateTime nextDay = date.plusDays(1).atStartOfDay();

        List<Appointment> appointments = session.createQuery(
                "FROM Appointment a " +
                "WHERE a.doctor.doctorId = :doctorId " +
                "AND a.scheduledStart >= :startOfDay " +
                "AND a.scheduledStart < :nextDay " +
                "ORDER BY a.scheduledStart",
                Appointment.class)
                .setParameter("doctorId", doctorId)
                .setParameter("startOfDay", startOfDay)
                .setParameter("nextDay", nextDay)
                .getResultList();

        if (appointments.isEmpty()) {
            System.out.println(
                    "No appointments found for this doctor on " + date);
            return;
        }

        System.out.println("\n===== DOCTOR'S DAILY SCHEDULE =====");

        for (Appointment a : appointments) {
            System.out.println("Appointment ID: "
                    + a.getAppointmentId());
            System.out.println("Patient: "
                    + a.getPatient().getFirstName() + " "
                    + a.getPatient().getLastName());
            System.out.println("Start: " + a.getScheduledStart());
            System.out.println("End: " + a.getScheduledEnd());
            System.out.println("Status: " + a.getStatus());
            System.out.println("----------------------------------");
        }

    } catch (Exception e) {
        System.out.println("Error retrieving doctor schedule: "
                + e.getMessage());
    }
}

// View appointment history for a patient
public void viewPatientAppointmentHistory(int patientId) {

    try (Session session =
                 HibernateUtil.getSessionFactory().openSession()) {

        Patient patient = session.get(Patient.class, patientId);

        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }

        List<Appointment> appointments = session.createQuery(
                "FROM Appointment a " +
                "WHERE a.patient.patientId = :patientId " +
                "ORDER BY a.scheduledStart DESC",
                Appointment.class)
                .setParameter("patientId", patientId)
                .getResultList();

        System.out.println("\n===== PATIENT APPOINTMENT HISTORY =====");
        System.out.println("Patient: " + patient.getFirstName()
                + " " + patient.getLastName());

        if (appointments.isEmpty()) {
            System.out.println("No appointments found for this patient.");
            return;
        }

        for (Appointment a : appointments) {
            System.out.println("Appointment ID: "
                    + a.getAppointmentId());

            System.out.println("Doctor: "
                    + a.getDoctor().getFirstName() + " "
                    + a.getDoctor().getLastName());

            System.out.println("Specialization: "
                    + a.getDoctor().getSpecialization());

            System.out.println("Start: " + a.getScheduledStart());
            System.out.println("End: " + a.getScheduledEnd());
            System.out.println("Status: " + a.getStatus());
            System.out.println("----------------------------------");
        }

    } catch (Exception e) {
        System.out.println("Error retrieving patient history: "
                + e.getMessage());
    }
}

// Cancel an appointment
public void cancelAppointment(int appointmentId, String reason) {

    Transaction transaction = null;

    try (Session session =
                 HibernateUtil.getSessionFactory().openSession()) {

        transaction = session.beginTransaction();

        Appointment appointment =
                session.get(Appointment.class, appointmentId);

        if (appointment == null) {
            System.out.println("Appointment not found!");
            transaction.rollback();
            return;
        }

        if (!"Scheduled".equalsIgnoreCase(appointment.getStatus())) {
            System.out.println(
                    "Only scheduled appointments can be cancelled.");
            transaction.rollback();
            return;
        }

        appointment.setStatus("Cancelled");
        appointment.setCancellationReason(reason);

        transaction.commit();

        System.out.println("Appointment cancelled successfully!");

    } catch (Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }

        System.out.println("Cancellation error: " + e.getMessage());
    }
}

// Count appointments grouped by status
public void viewAppointmentStatistics() {

    try (Session session =
                 HibernateUtil.getSessionFactory().openSession()) {

        List<Object[]> results = session.createQuery(
                "SELECT a.status, COUNT(a) " +
                "FROM Appointment a " +
                "GROUP BY a.status",
                Object[].class)
                .getResultList();

        System.out.println("\n===== APPOINTMENT STATISTICS =====");

        if (results.isEmpty()) {
            System.out.println("No appointment records found.");
            return;
        }

        for (Object[] row : results) {
            System.out.println(
                    row[0] + ": " + row[1]);
        }

    } catch (Exception e) {
        System.out.println(
                "Statistics error: " + e.getMessage());
    }
}

// View appointments scheduled for today
public void viewTodaysAppointments() {

    java.time.LocalDate today = java.time.LocalDate.now();

    java.time.LocalDateTime startOfDay = today.atStartOfDay();
    java.time.LocalDateTime nextDay = today.plusDays(1).atStartOfDay();

    try (Session session =
                 HibernateUtil.getSessionFactory().openSession()) {

        List<Appointment> appointments = session.createQuery(
                "FROM Appointment a " +
                "WHERE a.scheduledStart >= :start " +
                "AND a.scheduledStart < :end " +
                "ORDER BY a.scheduledStart",
                Appointment.class)
                .setParameter("start", startOfDay)
                .setParameter("end", nextDay)
                .getResultList();

        System.out.println("\n===== TODAY'S APPOINTMENTS =====");

        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled for today.");
            return;
        }

        for (Appointment a : appointments) {
            System.out.println(
                    "Appointment ID: " + a.getAppointmentId()
                    + " | Patient: "
                    + a.getPatient().getFirstName() + " "
                    + a.getPatient().getLastName()
                    + " | Doctor: "
                    + a.getDoctor().getFirstName() + " "
                    + a.getDoctor().getLastName()
                    + " | Start: " + a.getScheduledStart()
                    + " | Status: " + a.getStatus());
        }

    } catch (Exception e) {
        System.out.println(
                "Today's appointments error: " + e.getMessage());
    }
}
// Reschedule an existing appointment
public void rescheduleAppointment(
        int appointmentId,
        java.time.LocalDateTime newStart,
        java.time.LocalDateTime newEnd) {

    Transaction transaction = null;

    try (Session session =
                 HibernateUtil.getSessionFactory().openSession()) {

        transaction = session.beginTransaction();

        Appointment appointment =
                session.get(Appointment.class, appointmentId);

        if (appointment == null) {
            System.out.println("Appointment not found!");
            transaction.rollback();
            return;
        }

        if (!"Scheduled".equalsIgnoreCase(appointment.getStatus())) {
            System.out.println(
                    "Only scheduled appointments can be rescheduled.");
            transaction.rollback();
            return;
        }

        if (newStart == null || newEnd == null
                || !newEnd.isAfter(newStart)) {
            System.out.println("Invalid appointment time!");
            transaction.rollback();
            return;
        }

        // Check whether the doctor has another conflicting appointment.
        // Exclude the appointment being rescheduled.
        Long conflicts = session.createQuery(
                "SELECT COUNT(a) FROM Appointment a " +
                "WHERE a.doctor.doctorId = :doctorId " +
                "AND a.appointmentId <> :appointmentId " +
                "AND a.status = 'Scheduled' " +
                "AND a.scheduledStart < :newEnd " +
                "AND a.scheduledEnd > :newStart",
                Long.class)
                .setParameter(
                        "doctorId",
                        appointment.getDoctor().getDoctorId())
                .setParameter("appointmentId", appointmentId)
                .setParameter("newStart", newStart)
                .setParameter("newEnd", newEnd)
                .getSingleResult();

        if (conflicts > 0) {
            System.out.println(
                    "\nRescheduling failed: Doctor already has an "
                    + "appointment during this time.");
            transaction.rollback();
            return;
        }

        appointment.setScheduledStart(newStart);
        appointment.setScheduledEnd(newEnd);

        // Hibernate detects the changes and updates the record.
        transaction.commit();

        System.out.println("Appointment rescheduled successfully!");

    } catch (Exception e) {

        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }

        System.out.println(
                "Rescheduling error: " + e.getMessage());
    }
}

}