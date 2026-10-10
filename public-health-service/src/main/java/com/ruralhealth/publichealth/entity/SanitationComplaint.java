package com.ruralhealth.publichealth.entity;
import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="sanitation_complaints")
public class SanitationComplaint {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long facilityId;
 @Column(nullable=false) private String facilityName;
 @Column(nullable=false,columnDefinition="TEXT") private String description;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private WorkStatus status=WorkStatus.OPEN;
 @Column(nullable=false) private String reportedBy;
 @Column(columnDefinition="TEXT") private String resolution;
 @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
 @Column(nullable=false) private LocalDateTime updatedAt;
 @PrePersist void create(){LocalDateTime n=LocalDateTime.now();createdAt=n;updatedAt=n;}
 @PreUpdate void update(){updatedAt=LocalDateTime.now();}
 public SanitationComplaint(){}
 public Long getId(){return id;} public Long getFacilityId(){return facilityId;} public void setFacilityId(Long v){facilityId=v;}
 public String getFacilityName(){return facilityName;} public void setFacilityName(String v){facilityName=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public WorkStatus getStatus(){return status;} public void setStatus(WorkStatus v){status=v;}
 public String getReportedBy(){return reportedBy;} public void setReportedBy(String v){reportedBy=v;}
 public String getResolution(){return resolution;} public void setResolution(String v){resolution=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}
