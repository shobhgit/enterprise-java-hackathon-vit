package com.ruralhealth.publichealth.dto;
import com.ruralhealth.publichealth.entity.WaterRecord;
import com.ruralhealth.publichealth.entity.enums.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
public record WaterRecordResponse(Long id,String location,WaterSource source,String supplyInfo,LocalDate observedOn,WaterQuality qualityStatus,
 String issueDescription,String reportedBy,WaterActionStatus actionStatus,String correctiveAction,LocalDateTime createdAt) {
 public static WaterRecordResponse from(WaterRecord w){return new WaterRecordResponse(w.getId(),w.getLocation(),w.getSource(),w.getSupplyInfo(),w.getObservedOn(),w.getQualityStatus(),w.getIssueDescription(),w.getReportedBy(),w.getActionStatus(),w.getCorrectiveAction(),w.getCreatedAt());}
}
