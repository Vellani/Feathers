package com.example.feathers.model.entity;

import com.example.feathers.model.entity.enums.UserRolesEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    // Essential - Account
    private String username;
    private String email;
    private String password;
    private UserRolesEnum accountLevel;

    // Essential - Profile
    private String licenseNumber;

    // Non Essential - Personal
    private String firstName;
    private String lastName;
    private String address;

    public UserEntity() {
    }


    @Column(nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated
    public UserRolesEnum getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(UserRolesEnum accountLevel) {
        this.accountLevel = accountLevel;
    }

    @Column(unique = true)
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Column
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(columnDefinition = "TEXT")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
