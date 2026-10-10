package com.ruralhealth.publichealth.dto;
import com.ruralhealth.publichealth.entity.ReplenishmentRequest;
import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import java.time.LocalDateTime;
public record ReplenishmentResponse(Long id,Long medicineId,String medicineName,Integer quantityRequested,WorkStatus status,
 String requestedBy,String remarks,Long healthCentreId,String healthCentreName,LocalDateTime createdAt,LocalDateTime updatedAt) {
 public static ReplenishmentResponse from(ReplenishmentRequest r){return new ReplenishmentResponse(r.getId(),r.getMedicineId(),r.getMedicineName(),r.getQuantityRequested(),r.getStatus(),r.getRequestedBy(),r.getRemarks(),r.getHealthCentreId(),r.getHealthCentreName(),r.getCreatedAt(),r.getUpdatedAt());}
}
