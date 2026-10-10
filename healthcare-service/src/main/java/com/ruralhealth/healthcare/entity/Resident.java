
package com.ruralhealth.healthcare.entity;

import com.ruralhealth.healthcare.entity.enums.Gender;
import jakarta.persistence.*;
import java.time.LocalDate;
//entity tells jpad to manage this class as a db entity
@Entity
//maps the class to the table name residents in the db
@Table(name = "residents")
public class Resident {
    //used to mark pk
    @Id
    //automatic generation of id when inserting the value
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //same as not null constraints
    @Column(nullable = false)
    private String fullName;

    private LocalDate dateOfBirth;
    //convert enum to string
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    private String village;

    private String address;
//jpa needs this no arg const for some reason. i ssuppose to initialize entity for row retriewal or whateever
    public Resident() {
    }
//getter setters here are reqruired so that they can be accessed abe basic h
    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
