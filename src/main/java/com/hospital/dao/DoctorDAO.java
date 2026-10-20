package com.hospital.dao;

import com.hospital.entity.Doctor;
import com.hospital.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DoctorDAO {

        // Search doctors by specialization
        public void searchDoctorsBySpecialization(String specialization) {

                try (Session session = HibernateUtil.getSessionFactory().openSession()) {

                        List<Doctor> doctors = session.createQuery(
                                        "FROM Doctor WHERE LOWER(specialization) = LOWER(:specialization)",
                                        Doctor.class)
                                        .setParameter("specialization", specialization.trim())
                                        .getResultList();

                        if (doctors.isEmpty()) {
                                System.out.println(
                                                "No doctors found for specialization: " + specialization);
                                return;
                        }

                        for (Doctor doctor : doctors) {
                                System.out.println("Doctor ID: " + doctor.getDoctorId());
                                System.out.println("Name: " + doctor.getFirstName()
                                                + " " + doctor.getLastName());
                                System.out.println("Specialization: "
                                                + doctor.getSpecialization());
                                System.out.println("Phone: " + doctor.getPhone());
                                System.out.println("Consultation Fee: "
                                                + doctor.getConsultationFee());
                                System.out.println("Availability: "
                                                + doctor.getAvailabilityStatus());
                                System.out.println("------------------------------");
                        }

                } catch (Exception e) {
                        System.out.println(
                                        "Error searching doctors: " + e.getMessage());
                }
        }

        public void addDoctor(Doctor doctor) {

                Transaction transaction = null;

                try (Session session = HibernateUtil
                                .getSessionFactory()
                                .openSession()) {

                        transaction = session.beginTransaction();

                        session.persist(doctor);

                        transaction.commit();

                        System.out.println("\nDoctor registered successfully!");
                        System.out.println("Generated Doctor ID: "
                                        + doctor.getDoctorId());

                } catch (Exception e) {

                        if (transaction != null && transaction.isActive()) {
                                transaction.rollback();
                        }

                        System.out.println("Error registering doctor: "
                                        + e.getMessage());
                }
        }

        public void viewAllDoctors() {

                try (Session session = HibernateUtil
                                .getSessionFactory()
                                .openSession()) {

                        List<Doctor> doctors = session
                                        .createQuery("FROM Doctor", Doctor.class)
                                        .list();

                        if (doctors.isEmpty()) {
                                System.out.println("\nNo doctors found.");
                                return;
                        }

                        System.out.println("\n========== DOCTOR LIST ==========");

                        for (Doctor doctor : doctors) {

                                System.out.println("Doctor ID: "
                                                + doctor.getDoctorId());

                                System.out.println("Name: "
                                                + doctor.getFirstName() + " "
                                                + doctor.getLastName());

                                System.out.println("Specialization: "
                                                + doctor.getSpecialization());

                                System.out.println("Phone: "
                                                + doctor.getPhone());

                                System.out.println("Consultation Fee: "
                                                + doctor.getConsultationFee());

                                System.out.println("Availability: "
                                                + doctor.getAvailabilityStatus());

                                System.out.println("---------------------------------");
                        }

                } catch (Exception e) {
                        System.out.println("Error retrieving doctors: "
                                        + e.getMessage());
                }
        }
}