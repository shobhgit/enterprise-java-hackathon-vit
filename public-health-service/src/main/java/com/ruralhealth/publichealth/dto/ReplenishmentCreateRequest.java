package com.ruralhealth.publichealth.dto;

import jakarta.validation.constraints.*;

public class ReplenishmentCreateRequest {
 @NotNull(message="medicineId is required") @Min(value=1, message="medicineId must be greater than 0")
 private Long medicineId;
 @NotNull(message="quantityRequested is required") @Min(value=1, message="quantityRequested must be greater than 0")
 private Integer quantityRequested;
 @NotBlank(message="requestedBy must not be blank")
 private String requestedBy;
 private String remarks;

 public ReplenishmentCreateRequest() {}
 public Long getMedicineId(){return medicineId;} public void setMedicineId(Long v){medicineId=v;}
 public Integer getQuantityRequested(){return quantityRequested;} public void setQuantityRequested(Integer v){quantityRequested=v;}
 public String getRequestedBy(){return requestedBy;} public void setRequestedBy(String v){requestedBy=v;}
 public String getRemarks(){return remarks;} public void setRemarks(String v){remarks=v;}
}
