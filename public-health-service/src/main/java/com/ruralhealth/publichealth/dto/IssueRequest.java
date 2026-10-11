package com.ruralhealth.publichealth.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class IssueRequest {
 @NotNull(message="quantity is required") @Min(value=1, message="quantity must be greater than 0")
 private Integer quantity;
 @NotBlank(message="issuedTo must not be blank")
 private String issuedTo;
 private String reason;
 @NotNull(message="issuedOn is required")
 private LocalDate issuedOn;

 public IssueRequest() {}
 public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
 public String getIssuedTo(){return issuedTo;} public void setIssuedTo(String v){issuedTo=v;}
 public String getReason(){return reason;} public void setReason(String v){reason=v;}
 public LocalDate getIssuedOn(){return issuedOn;} public void setIssuedOn(LocalDate v){issuedOn=v;}
}
