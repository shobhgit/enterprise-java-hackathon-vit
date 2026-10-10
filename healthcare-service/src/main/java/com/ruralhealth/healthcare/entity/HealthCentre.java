package com.ruralhealth.healthcare.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "health_centres")
public class HealthCentre {
    //id,name,location, are the only thing i could come up with
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    public HealthCentre() {
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
}
