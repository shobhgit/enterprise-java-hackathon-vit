package com.ruralhealth.publichealth.dto;

import com.ruralhealth.publichealth.entity.enums.WaterActionStatus;
import jakarta.validation.constraints.*;

public class WaterActionRequest {
 @NotNull(message="actionStatus is required")
 private WaterActionStatus actionStatus;
 private String correctiveAction;

 public WaterActionRequest() {}
 public WaterActionStatus getActionStatus(){return actionStatus;} public void setActionStatus(WaterActionStatus v){actionStatus=v;}
 public String getCorrectiveAction(){return correctiveAction;} public void setCorrectiveAction(String v){correctiveAction=v;}
}
