package com.ruralhealth.publichealth.dto;
import com.ruralhealth.publichealth.entity.SanitationFacility;
import com.ruralhealth.publichealth.entity.enums.*;
import java.time.LocalDate;
public record FacilityResponse(Long id,String name,FacilityType type,String location,CleanlinessStatus cleanlinessStatus,LocalDate lastMaintenanceDate) {
 public static FacilityResponse from(SanitationFacility f){return new FacilityResponse(f.getId(),f.getName(),f.getType(),f.getLocation(),f.getCleanlinessStatus(),f.getLastMaintenanceDate());}
}
