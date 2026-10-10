package com.ruralhealth.publichealth.dto;

import com.ruralhealth.publichealth.entity.enums.CleanlinessStatus;
import jakarta.validation.constraints.*;

public class CleanlinessRequest {
 @NotNull(message="cleanlinessStatus is required")
 private CleanlinessStatus cleanlinessStatus;

 public CleanlinessRequest() {}
 public CleanlinessStatus getCleanlinessStatus(){return cleanlinessStatus;} public void setCleanlinessStatus(CleanlinessStatus v){cleanlinessStatus=v;}
}
