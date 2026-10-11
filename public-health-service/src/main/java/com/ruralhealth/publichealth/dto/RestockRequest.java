package com.ruralhealth.publichealth.dto;

import jakarta.validation.constraints.*;

public class RestockRequest {
 @NotNull(message="quantity is required") @Min(value=1, message="quantity must be greater than 0")
 private Integer quantity;
 private String note;

 public RestockRequest() {}
 public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
 public String getNote(){return note;} public void setNote(String v){note=v;}
}
