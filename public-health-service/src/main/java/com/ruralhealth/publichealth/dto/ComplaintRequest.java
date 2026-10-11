package com.ruralhealth.publichealth.dto;

import jakarta.validation.constraints.*;

public class ComplaintRequest {
 @NotNull(message="facilityId is required") @Min(value=1, message="facilityId must be greater than 0")
 private Long facilityId;
 @NotBlank(message="description must not be blank")
 private String description;
 @NotBlank(message="reportedBy must not be blank")
 private String reportedBy;

 public ComplaintRequest() {}
 public Long getFacilityId(){return facilityId;} public void setFacilityId(Long v){facilityId=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getReportedBy(){return reportedBy;} public void setReportedBy(String v){reportedBy=v;}
}
