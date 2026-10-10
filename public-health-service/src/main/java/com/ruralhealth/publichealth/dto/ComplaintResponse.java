package com.ruralhealth.publichealth.dto;
import com.ruralhealth.publichealth.entity.SanitationComplaint;
import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import java.time.LocalDateTime;
public record ComplaintResponse(Long id,Long facilityId,String facilityName,String description,WorkStatus status,String reportedBy,String resolution,LocalDateTime createdAt,LocalDateTime updatedAt) {
 public static ComplaintResponse from(SanitationComplaint c){return new ComplaintResponse(c.getId(),c.getFacilityId(),c.getFacilityName(),c.getDescription(),c.getStatus(),c.getReportedBy(),c.getResolution(),c.getCreatedAt(),c.getUpdatedAt());}
}
