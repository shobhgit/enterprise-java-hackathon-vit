package com.ruralhealth.healthcare.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "health_workers")
public class HealthWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    private String phone;
    private String designation;
//performing many to one relationship as many workers can belong to single centre
    //fetchtype lazy will not let jpa load the centre untill it is required
    //setting optional as false will make every worker to belong to a centre
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //this is the way to setup foreing key in the health worker table
    //this is why we are creteing helthcenter object;
    @JoinColumn(name = "health_centre_id", nullable = false)
    private HealthCentre healthCentre;


    public HealthWorker() {
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public HealthCentre getHealthCentre() {
        return healthCentre;
    }

    public void setHealthCentre(HealthCentre healthCentre) {
        this.healthCentre = healthCentre;
    }
}
