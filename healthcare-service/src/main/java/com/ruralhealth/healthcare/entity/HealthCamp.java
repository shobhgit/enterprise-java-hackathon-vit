
package com.ruralhealth.healthcare.entity;

import com.ruralhealth.healthcare.entity.enums.CampStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "health_camps")
public class HealthCamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampStatus status = CampStatus.PLANNED;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_centre_id")
    private HealthCentre healthCentre;
    public HealthCamp() {
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public CampStatus getStatus() {
        return status;
    }

    public void setStatus(CampStatus status) {
        this.status = status;
    }

    public HealthCentre getHealthCentre() {
        return healthCentre;
    }

    public void setHealthCentre(HealthCentre healthCentre) {
        this.healthCentre = healthCentre;
    }
}
