package com.hospital;

import com.hospital.dao.AppointmentDAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import com.hospital.dao.PatientDAO;
import com.hospital.entity.Patient;
import com.hospital.util.HibernateUtil;
import com.hospital.dao.DoctorDAO;
import com.hospital.entity.Doctor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PatientDAO patientDAO = new PatientDAO();
        DoctorDAO doctorDAO = new DoctorDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        try {
            int choice = 0;

            do {
                System.out.println("\n====================================");
                System.out.println("     HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("====================================");

                System.out.print("1. Register Patient\t\t\t");
                System.out.println("2. View All Patients");
                System.out.print("3. Search Patient by ID\t\t\t");
                System.out.println("4. Update Patient Details");
                System.out.print("5. Register Doctor\t\t\t");
                System.out.println("6. View All Doctors");
                System.out.print("7. Book Appointment\t\t\t");
                System.out.println("8. View All Appointments");
                System.out.print("9. Search Patient by Phone\t\t");
                System.out.println("10. Search Doctors by Specialization");
                System.out.print("11. View Doctor's Daily Schedule\t");
                System.out.println("12. View Patient Appointment History");
                System.out.print("13. Cancel Appointment\t\t\t");
                System.out.println("14. Appointment Statistics");
                System.out.print("15. View Today's Appointments\t\t");
                System.out.println("16. Reschudle Appointment");
                System.out.println("17. Exit.\t\t");

                System.out.print("\nEnter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine();
                    continue;
                }

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:

                        System.out.print("Enter first name: ");
                        String firstName = scanner.nextLine();

                        System.out.print("Enter last name: ");
                        String lastName = scanner.nextLine();

                        System.out.print("Enter phone number: ");
                        String phone = scanner.nextLine();

                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();

                        System.out.print("Enter gender: ");
                        String gender = scanner.nextLine();

                        System.out.print("Enter address: ");
                        String address = scanner.nextLine();

                        System.out.print("Enter date of birth (YYYY-MM-DD): ");
                        String dobInput = scanner.nextLine();

                        try {
                            LocalDate dob = LocalDate.parse(dobInput);

                            Patient patient = new Patient(
                                    firstName,
                                    lastName,
                                    phone,
                                    email,
                                    gender,
                                    address);

                            patient.setDateOfBirth(dob);
                            patientDAO.addPatient(patient);

                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println(
                                    "Invalid date! Use YYYY-MM-DD format.");
                        }

                        break;

                    case 2:

                        patientDAO.viewAllPatients();
                        break;

                    case 3:

                        System.out.print("Enter Patient ID: ");
                        int patientId = scanner.nextInt();
                        scanner.nextLine();

                        patientDAO.searchPatientById(patientId);
                        break;

                    case 4:

                        System.out.print("Enter Patient ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter new phone number: ");
                        String newPhone = scanner.nextLine();

                        System.out.print("Enter new email: ");
                        String newEmail = scanner.nextLine();

                        patientDAO.updatePatient(
                                updateId,
                                newPhone,
                                newEmail);

                        break;

                    case 5:

                        System.out.print("Enter doctor's first name: ");
                        String doctorFirstName = scanner.nextLine();

                        System.out.print("Enter doctor's last name: ");
                        String doctorLastName = scanner.nextLine();

                        System.out.print("Enter specialization: ");
                        String specialization = scanner.nextLine();

                        System.out.print("Enter phone number: ");
                        String doctorPhone = scanner.nextLine();

                        System.out.print("Enter consultation fee: ");
                        BigDecimal fee = new BigDecimal(scanner.nextLine().trim());

                        Doctor doctor = new Doctor(
                                doctorFirstName,
                                doctorLastName,
                                specialization,
                                doctorPhone,
                                fee);

                        doctorDAO.addDoctor(doctor);

                        break;

                    case 6:

                        doctorDAO.viewAllDoctors();

                        break;

                    case 7:
                        try {
                            System.out.print("Enter Patient ID: ");
                            int appointmentPatientId = Integer.parseInt(scanner.nextLine().trim());

                            System.out.print("Enter Doctor ID: ");
                            int doctorId = Integer.parseInt(scanner.nextLine().trim());

                            System.out.print("Enter Start Date and Time (yyyy-MM-ddTHH:mm): ");
                            LocalDateTime start = LocalDateTime.parse(scanner.nextLine().trim());

                            System.out.print("Enter End Date and Time (yyyy-MM-ddTHH:mm): ");
                            LocalDateTime end = LocalDateTime.parse(scanner.nextLine().trim());

                            System.out.print("Enter Reason for Appointment: ");
                            String reason = scanner.nextLine();

                            appointmentDAO.bookAppointment(
                                    appointmentPatientId, doctorId, start, end, reason);

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID! Please enter numeric IDs.");
                        } catch (DateTimeParseException e) {
                            System.out.println(
                                    "Invalid date/time format! Use yyyy-MM-ddTHH:mm.");
                        }
                        break;
                    case 8:
                        appointmentDAO.viewAllAppointments();
                        break;

                    case 9:
                        System.out.print("Enter Patient Phone Number: ");
                        String searchPhone = scanner.nextLine().trim();

                        patientDAO.searchPatientByPhone(searchPhone);
                        break;

                    case 10:
                        System.out.print("Enter Doctor Specialization: ");
                        String searchSpecialization = scanner.nextLine().trim();

                        doctorDAO.searchDoctorsBySpecialization(searchSpecialization);
                        break;

                    case 11:
                        try {
                            System.out.print("Enter Doctor ID: ");
                            int scheduleDoctorId = Integer.parseInt(scanner.nextLine().trim());

                            System.out.print("Enter Date (yyyy-MM-dd): ");
                            java.time.LocalDate scheduleDate = java.time.LocalDate.parse(scanner.nextLine().trim());

                            appointmentDAO.viewDoctorSchedule(
                                    scheduleDoctorId, scheduleDate);

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid doctor ID. Enter a number.");
                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println("Invalid date. Use yyyy-MM-dd.");
                        }
                        break;

                    case 12:
                        try {
                            System.out.print("Enter Patient ID: ");

                            int historyPatientId = Integer.parseInt(scanner.nextLine().trim());

                            appointmentDAO.viewPatientAppointmentHistory(
                                    historyPatientId);

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Patient ID! Enter a number.");
                        }
                        break;

                    case 13:
                        try {
                            System.out.print("Enter Appointment ID: ");
                            int cancelAppointmentId = Integer.parseInt(scanner.nextLine().trim());

                            System.out.print("Enter Cancellation Reason: ");
                            String cancelReason = scanner.nextLine().trim();

                            appointmentDAO.cancelAppointment(
                                    cancelAppointmentId, cancelReason);

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Appointment ID!");
                        }
                        break;
                    case 14:
                        appointmentDAO.viewAppointmentStatistics();
                        break;
                    case 15:
                        appointmentDAO.viewTodaysAppointments();
                        break;
                    case 16:
                        try {
                            System.out.print("Enter Appointment ID to Reschedule: ");
                            int rescheduleId = Integer.parseInt(scanner.nextLine().trim());

                            System.out.print(
                                    "Enter New Start Date and Time (yyyy-MM-ddTHH:mm): ");
                            java.time.LocalDateTime newStart = java.time.LocalDateTime.parse(
                                    scanner.nextLine().trim());

                            System.out.print(
                                    "Enter New End Date and Time (yyyy-MM-ddTHH:mm): ");
                            java.time.LocalDateTime newEnd = java.time.LocalDateTime.parse(
                                    scanner.nextLine().trim());

                            appointmentDAO.rescheduleAppointment(
                                    rescheduleId, newStart, newEnd);

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Appointment ID!");
                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println(
                                    "Invalid date/time! Use yyyy-MM-ddTHH:mm.");
                        }
                        break;
                    case 17:
                        System.out.println("Exiting Hospital Management System...");
                        break;

                    default:

                        System.out.println("Invalid choice! Try again.");
                }

            } while (choice != 17);

        } finally {
            scanner.close();
            HibernateUtil.shutdown();
        }
    }
}