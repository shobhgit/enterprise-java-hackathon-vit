package com.ruralhealth.publichealth.dto;

import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import jakarta.validation.constraints.*;

public class ComplaintStatusRequest {
 @NotNull(message="status is required")
 private WorkStatus status;
 private String resolution;

 public ComplaintStatusRequest() {}
 public WorkStatus getStatus(){return status;} public void setStatus(WorkStatus v){status=v;}
 public String getResolution(){return resolution;} public void setResolution(String v){resolution=v;}
}
