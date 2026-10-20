package com.hospital.dao;

import com.hospital.entity.Patient;
import com.hospital.util.HibernateUtil;
import java.util.List;
// import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PatientDAO {

    public void updatePatient(int patientId, String phone, String email) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            Patient patient = session.get(Patient.class, patientId);

            if (patient == null) {
                System.out.println("Patient not found!");
                transaction.rollback();
                return;
            }

            patient.setPhone(phone);
            patient.setEmail(email);

            transaction.commit();

            System.out.println("Patient details updated successfully!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println("Error updating patient: " + e.getMessage());
        }
    }

    public void searchPatientById(int patientId) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            Patient patient = session.get(
                    Patient.class,
                    patientId);

            if (patient == null) {
                System.out.println("Patient not found!");
                return;
            }

            System.out.println("\n========== PATIENT DETAILS ==========");

            System.out.println("Patient ID: " + patient.getPatientId());

            System.out.println("Name: "
                    + patient.getFirstName() + " "
                    + patient.getLastName());

            System.out.println("Date of Birth: "
                    + patient.getDateOfBirth());

            System.out.println("Gender: " + patient.getGender());

            System.out.println("Phone: " + patient.getPhone());

            System.out.println("Email: " + patient.getEmail());

            System.out.println("Address: " + patient.getAddress());

            System.out.println("Account Status: "
                    + patient.getAccountStatus());

            System.out.println("======================================");

        } catch (Exception e) {
            System.out.println("Error searching for patient: "
                    + e.getMessage());
        }
    }

    // Search patient by phone number
    public void searchPatientByPhone(String phone) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Patient> patients = session.createQuery(
                    "FROM Patient WHERE phone = :phone",
                    Patient.class)
                    .setParameter("phone", phone)
                    .getResultList();

            if (patients.isEmpty()) {
                System.out.println("No patient found with this phone number.");
                return;
            }

            for (Patient patient : patients) {
                System.out.println("Patient ID: " + patient.getPatientId());
                System.out.println("Name: " + patient.getFirstName()
                        + " " + patient.getLastName());
                System.out.println("Phone: " + patient.getPhone());
                System.out.println("Email: " + patient.getEmail());
                System.out.println("Account Status: "
                        + patient.getAccountStatus());
                System.out.println("------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Error searching patient: " + e.getMessage());
        }
    }

    public void addPatient(Patient patient) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.persist(patient);

            transaction.commit();

            System.out.println("\nPatient registered successfully!");
            System.out.println("Generated Patient ID: "
                    + patient.getPatientId());

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println("Error registering patient: "
                    + e.getMessage());
        }
    }

    public void viewAllPatients() {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            List<Patient> patients = session
                    .createQuery("FROM Patient", Patient.class)
                    .list();

            if (patients.isEmpty()) {
                System.out.println("\nNo patients found.");
                return;
            }

            System.out.println("\n========== PATIENT LIST ==========");

            for (Patient patient : patients) {

                System.out.println("Patient ID: "
                        + patient.getPatientId());

                System.out.println("Name: "
                        + patient.getFirstName() + " "
                        + patient.getLastName());

                System.out.println("Phone: "
                        + patient.getPhone());

                System.out.println("Email: "
                        + patient.getEmail());

                System.out.println("Date of Birth: "
                        + patient.getDateOfBirth());

                System.out.println("----------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Error retrieving patients: "
                    + e.getMessage());
        }
    }
}