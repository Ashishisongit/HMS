package com.hospital.entity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "patient_id")
private int patientId;

@Column(name = "first_name", nullable = false, length = 50)
private String firstName;

@Column(name = "last_name", nullable = false, length = 50)
private String lastName;

@Column(name = "date_of_birth")
private LocalDate dateOfBirth;

@Column(name = "gender", length = 15)
private String gender;

@Column(name = "phone", nullable = false, length = 15)
private String phone;

@Column(name = "email", length = 100)
private String email;

@Column(name = "address", length = 255)
private String address;

@Column(name = "account_status", nullable = false, length = 20)
private String accountStatus = "Active";

@Column(name = "emergency_contact_name", length = 100)
private String emergencyContactName;

@Column(name = "emergency_contact_phone", length = 15)
private String emergencyContactPhone;

@Column(name = "updated_at", insertable = false, updatable = false)
private java.time.LocalDateTime updatedAt;

public Patient() {
}

public Patient(String firstName, String lastName, String phone,
               String email, String gender, String address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.gender = gender;
    this.address = address;
    this.accountStatus = "Active";
}

public int getPatientId() {
    return patientId;
}

public String getFirstName() {
    return firstName;
}

public void setFirstName(String firstName) {
    this.firstName = firstName;
}

public String getLastName() {
    return lastName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}

public LocalDate getDateOfBirth() {
    return dateOfBirth;
}

public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
}

public String getGender() {
    return gender;
}

public void setGender(String gender) {
    this.gender = gender;
}

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}

public String getAccountStatus() {
    return accountStatus;
}

public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
}

public String getEmergencyContactName() {
    return emergencyContactName;
}

public void setEmergencyContactName(String emergencyContactName) {
    this.emergencyContactName = emergencyContactName;
}

public String getEmergencyContactPhone() {
    return emergencyContactPhone;
}

public void setEmergencyContactPhone(String emergencyContactPhone) {
    this.emergencyContactPhone = emergencyContactPhone;
}

public LocalDateTime getUpdatedAt() {
    return updatedAt;
}

}
