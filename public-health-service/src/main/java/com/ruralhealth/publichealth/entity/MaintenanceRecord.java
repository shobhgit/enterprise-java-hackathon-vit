package com.ruralhealth.publichealth.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="maintenance_records")
public class MaintenanceRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long facilityId;
 @Column(nullable=false) private LocalDate performedOn;
 @Column(nullable=false,columnDefinition="TEXT") private String description;
 @Column(nullable=false) private String performedBy;
 public MaintenanceRecord(){}
 public Long getId(){return id;} public Long getFacilityId(){return facilityId;} public void setFacilityId(Long v){facilityId=v;}
 public LocalDate getPerformedOn(){return performedOn;} public void setPerformedOn(LocalDate v){performedOn=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getPerformedBy(){return performedBy;} public void setPerformedBy(String v){performedBy=v;}
}
