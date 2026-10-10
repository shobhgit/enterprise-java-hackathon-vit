package com.ruralhealth.publichealth.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="medicine_issues")
public class MedicineIssue {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long medicineId;
 @Column(nullable=false) private Integer quantity;
 @Column(nullable=false) private String issuedTo;
 private String reason;
 @Column(nullable=false) private LocalDate issuedOn;
 @Column(nullable=false) private Integer remainingQuantity;
 public MedicineIssue(){}
 public Long getId(){return id;} public Long getMedicineId(){return medicineId;} public void setMedicineId(Long v){medicineId=v;}
 public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
 public String getIssuedTo(){return issuedTo;} public void setIssuedTo(String v){issuedTo=v;}
 public String getReason(){return reason;} public void setReason(String v){reason=v;}
 public LocalDate getIssuedOn(){return issuedOn;} public void setIssuedOn(LocalDate v){issuedOn=v;}
 public Integer getRemainingQuantity(){return remainingQuantity;} public void setRemainingQuantity(Integer v){remainingQuantity=v;}
}
