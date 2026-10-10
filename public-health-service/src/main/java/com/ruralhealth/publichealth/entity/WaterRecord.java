package com.ruralhealth.publichealth.entity;
import com.ruralhealth.publichealth.entity.enums.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity @Table(name="water_records")
public class WaterRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String location;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private WaterSource source;
 private String supplyInfo;
 @Column(nullable=false) private LocalDate observedOn;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private WaterQuality qualityStatus;
 @Column(columnDefinition="TEXT") private String issueDescription;
 @Column(nullable=false) private String reportedBy;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private WaterActionStatus actionStatus;
 @Column(columnDefinition="TEXT") private String correctiveAction;
 @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
 @PrePersist void create(){createdAt=LocalDateTime.now();}
 public WaterRecord(){}
 public Long getId(){return id;} public String getLocation(){return location;} public void setLocation(String v){location=v;}
 public WaterSource getSource(){return source;} public void setSource(WaterSource v){source=v;}
 public String getSupplyInfo(){return supplyInfo;} public void setSupplyInfo(String v){supplyInfo=v;}
 public LocalDate getObservedOn(){return observedOn;} public void setObservedOn(LocalDate v){observedOn=v;}
 public WaterQuality getQualityStatus(){return qualityStatus;} public void setQualityStatus(WaterQuality v){qualityStatus=v;}
 public String getIssueDescription(){return issueDescription;} public void setIssueDescription(String v){issueDescription=v;}
 public String getReportedBy(){return reportedBy;} public void setReportedBy(String v){reportedBy=v;}
 public WaterActionStatus getActionStatus(){return actionStatus;} public void setActionStatus(WaterActionStatus v){actionStatus=v;}
 public String getCorrectiveAction(){return correctiveAction;} public void setCorrectiveAction(String v){correctiveAction=v;}
 public LocalDateTime getCreatedAt(){return createdAt;}
}
