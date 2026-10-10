package com.ruralhealth.publichealth.dto;

import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import jakarta.validation.constraints.*;

public class StatusUpdateRequest {
 @NotNull(message="status is required")
 private WorkStatus status;

 public StatusUpdateRequest() {}
 public WorkStatus getStatus(){return status;} public void setStatus(WorkStatus v){status=v;}
}
