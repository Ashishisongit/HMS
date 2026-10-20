package com.hospital.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int doctorId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "specialization", nullable = false, length = 100)
    private String specialization;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "consultation_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(name = "max_daily_capacity", nullable = false)
    private int maxDailyCapacity = 10;

    @Column(name = "availability_status", nullable = false)
    private String availabilityStatus = "Available";

    @Column(name = "account_status", nullable = false)
    private String accountStatus = "Active";

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "years_of_experience", nullable = false)
    private int yearsOfExperience = 0;

    @Column(name = "room_number", length = 10)
    private String roomNumber;

    public Doctor() {
    }

    public Doctor(String firstName, String lastName,
            String specialization, String phone,
            BigDecimal consultationFee) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.phone = phone;
        this.consultationFee = consultationFee;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public int getMaxDailyCapacity() {
        return maxDailyCapacity;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    public void setMaxDailyCapacity(int maxDailyCapacity) {
        this.maxDailyCapacity = maxDailyCapacity;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

}