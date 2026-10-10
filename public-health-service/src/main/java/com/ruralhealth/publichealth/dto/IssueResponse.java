package com.ruralhealth.publichealth.dto;
import com.ruralhealth.publichealth.entity.MedicineIssue;
import java.time.LocalDate;
public record IssueResponse(Long id,Long medicineId,Integer quantity,String issuedTo,String reason,LocalDate issuedOn,Integer remainingQuantity) {
 public static IssueResponse from(MedicineIssue i){return new IssueResponse(i.getId(),i.getMedicineId(),i.getQuantity(),i.getIssuedTo(),i.getReason(),i.getIssuedOn(),i.getRemainingQuantity());}
}
