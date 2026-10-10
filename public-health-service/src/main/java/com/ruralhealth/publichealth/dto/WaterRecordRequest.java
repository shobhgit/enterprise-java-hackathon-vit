package com.ruralhealth.publichealth.dto;

import com.ruralhealth.publichealth.entity.enums.WaterQuality;
import com.ruralhealth.publichealth.entity.enums.WaterSource;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class WaterRecordRequest {
 @NotBlank(message="location must not be blank")
 private String location;
 @NotNull(message="source is required")
 private WaterSource source;
 private String supplyInfo;
 @NotNull(message="observedOn is required")
 private LocalDate observedOn;
 @NotNull(message="qualityStatus is required")
 private WaterQuality qualityStatus;
 private String issueDescription;
 @NotBlank(message="reportedBy must not be blank")
 private String reportedBy;

 public WaterRecordRequest() {}
 public String getLocation(){return location;} public void setLocation(String v){location=v;}
 public WaterSource getSource(){return source;} public void setSource(WaterSource v){source=v;}
 public String getSupplyInfo(){return supplyInfo;} public void setSupplyInfo(String v){supplyInfo=v;}
 public LocalDate getObservedOn(){return observedOn;} public void setObservedOn(LocalDate v){observedOn=v;}
 public WaterQuality getQualityStatus(){return qualityStatus;} public void setQualityStatus(WaterQuality v){qualityStatus=v;}
 public String getIssueDescription(){return issueDescription;} public void setIssueDescription(String v){issueDescription=v;}
 public String getReportedBy(){return reportedBy;} public void setReportedBy(String v){reportedBy=v;}
}
