package com.ruralhealth.publichealth.entity;
import com.ruralhealth.publichealth.entity.enums.MedicineType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="medicines")
public class Medicine {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private MedicineType type;
 @Column(nullable=false) private String unit;
 @Column(nullable=false) private Integer quantity;
 @Column(nullable=false) private Integer reorderLevel;
 @Column(nullable=false) private Long healthCentreId;
 @Column(nullable=false) private String healthCentreName;
 @Column(nullable=false) private LocalDateTime updatedAt;
 @PrePersist @PreUpdate void updateTimestamp(){updatedAt=LocalDateTime.now();}
 public Medicine(){}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public MedicineType getType(){return type;} public void setType(MedicineType v){type=v;}
 public String getUnit(){return unit;} public void setUnit(String v){unit=v;}
 public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
 public Integer getReorderLevel(){return reorderLevel;} public void setReorderLevel(Integer v){reorderLevel=v;}
 public Long getHealthCentreId(){return healthCentreId;} public void setHealthCentreId(Long v){healthCentreId=v;}
 public String getHealthCentreName(){return healthCentreName;} public void setHealthCentreName(String v){healthCentreName=v;}
 public LocalDateTime getUpdatedAt(){return updatedAt;}
 @Transient public boolean isLowStock(){return quantity!=null && reorderLevel!=null && quantity<=reorderLevel;}
}
