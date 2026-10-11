package com.ruralhealth.publichealth.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class MaintenanceRequest {
 @NotNull(message="performedOn is required")
 private LocalDate performedOn;
 @NotBlank(message="description must not be blank")
 private String description;
 @NotBlank(message="performedBy must not be blank")
 private String performedBy;

 public MaintenanceRequest() {}
 public LocalDate getPerformedOn(){return performedOn;} public void setPerformedOn(LocalDate v){performedOn=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getPerformedBy(){return performedBy;} public void setPerformedBy(String v){performedBy=v;}
}
