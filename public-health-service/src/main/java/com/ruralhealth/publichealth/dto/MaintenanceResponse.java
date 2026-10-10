package com.ruralhealth.publichealth.dto;
import com.ruralhealth.publichealth.entity.MaintenanceRecord;
import java.time.LocalDate;
public record MaintenanceResponse(Long id,Long facilityId,LocalDate performedOn,String description,String performedBy) {
 public static MaintenanceResponse from(MaintenanceRecord m){return new MaintenanceResponse(m.getId(),m.getFacilityId(),m.getPerformedOn(),m.getDescription(),m.getPerformedBy());}
}
