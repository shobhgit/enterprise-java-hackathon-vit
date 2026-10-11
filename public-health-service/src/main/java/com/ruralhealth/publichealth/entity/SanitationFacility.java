package com.ruralhealth.publichealth.entity;
import com.ruralhealth.publichealth.entity.enums.*;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="sanitation_facilities")
public class SanitationFacility {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private FacilityType type;
 @Column(nullable=false) private String location;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private CleanlinessStatus cleanlinessStatus;
 private LocalDate lastMaintenanceDate;
 public SanitationFacility(){}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public FacilityType getType(){return type;} public void setType(FacilityType v){type=v;}
 public String getLocation(){return location;} public void setLocation(String v){location=v;}
 public CleanlinessStatus getCleanlinessStatus(){return cleanlinessStatus;} public void setCleanlinessStatus(CleanlinessStatus v){cleanlinessStatus=v;}
 public LocalDate getLastMaintenanceDate(){return lastMaintenanceDate;} public void setLastMaintenanceDate(LocalDate v){lastMaintenanceDate=v;}
}
