package com.ruralhealth.publichealth.dto;
import com.ruralhealth.publichealth.entity.Medicine;
import com.ruralhealth.publichealth.entity.enums.MedicineType;
import java.time.LocalDateTime;
public record MedicineResponse(Long id,String name,MedicineType type,String unit,Integer quantity,Integer reorderLevel,
 boolean lowStock,Long healthCentreId,String healthCentreName,LocalDateTime updatedAt) {
 public static MedicineResponse from(Medicine m){return new MedicineResponse(m.getId(),m.getName(),m.getType(),m.getUnit(),m.getQuantity(),m.getReorderLevel(),m.isLowStock(),m.getHealthCentreId(),m.getHealthCentreName(),m.getUpdatedAt());}
}
