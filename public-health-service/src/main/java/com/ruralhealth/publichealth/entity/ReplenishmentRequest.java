package com.ruralhealth.publichealth.entity;
import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="replenishment_requests")
public class ReplenishmentRequest {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long medicineId;
 @Column(nullable=false) private String medicineName;
 @Column(nullable=false) private Integer quantityRequested;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private WorkStatus status=WorkStatus.OPEN;
 @Column(nullable=false) private String requestedBy;
 @Column(columnDefinition="TEXT") private String remarks;
 @Column(nullable=false) private Long healthCentreId;
 @Column(nullable=false) private String healthCentreName;
 @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
 @Column(nullable=false) private LocalDateTime updatedAt;
 @PrePersist void create(){LocalDateTime n=LocalDateTime.now();createdAt=n;updatedAt=n;}
 @PreUpdate void update(){updatedAt=LocalDateTime.now();}
 public ReplenishmentRequest(){}
 public Long getId(){return id;} public Long getMedicineId(){return medicineId;} public void setMedicineId(Long v){medicineId=v;}
 public String getMedicineName(){return medicineName;} public void setMedicineName(String v){medicineName=v;}
 public Integer getQuantityRequested(){return quantityRequested;} public void setQuantityRequested(Integer v){quantityRequested=v;}
 public WorkStatus getStatus(){return status;} public void setStatus(WorkStatus v){status=v;}
 public String getRequestedBy(){return requestedBy;} public void setRequestedBy(String v){requestedBy=v;}
 public String getRemarks(){return remarks;} public void setRemarks(String v){remarks=v;}
 public Long getHealthCentreId(){return healthCentreId;} public void setHealthCentreId(Long v){healthCentreId=v;}
 public String getHealthCentreName(){return healthCentreName;} public void setHealthCentreName(String v){healthCentreName=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}
