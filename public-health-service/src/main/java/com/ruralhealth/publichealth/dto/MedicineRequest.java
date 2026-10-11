package com.ruralhealth.publichealth.dto;

import com.ruralhealth.publichealth.entity.enums.MedicineType;
import jakarta.validation.constraints.*;

public class MedicineRequest {
 @NotBlank(message="name must not be blank")
 private String name;
 @NotNull(message="type is required")
 private MedicineType type;
 @NotBlank(message="unit must not be blank")
 private String unit;
 @NotNull(message="quantity is required") @Min(value=0, message="quantity must be at least 0")
 private Integer quantity;
 @NotNull(message="reorderLevel is required") @Min(value=0, message="reorderLevel must be at least 0")
 private Integer reorderLevel;
 @NotNull(message="healthCentreId is required") @Min(value=1, message="healthCentreId must be greater than 0")
 private Long healthCentreId;
 @NotBlank(message="healthCentreName must not be blank")
 private String healthCentreName;

 public MedicineRequest() {}
 public String getName(){return name;} public void setName(String v){name=v;}
 public MedicineType getType(){return type;} public void setType(MedicineType v){type=v;}
 public String getUnit(){return unit;} public void setUnit(String v){unit=v;}
 public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
 public Integer getReorderLevel(){return reorderLevel;} public void setReorderLevel(Integer v){reorderLevel=v;}
 public Long getHealthCentreId(){return healthCentreId;} public void setHealthCentreId(Long v){healthCentreId=v;}
 public String getHealthCentreName(){return healthCentreName;} public void setHealthCentreName(String v){healthCentreName=v;}
}
