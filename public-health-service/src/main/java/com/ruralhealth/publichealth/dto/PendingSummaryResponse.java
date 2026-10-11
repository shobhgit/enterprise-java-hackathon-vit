package com.ruralhealth.publichealth.dto;
import java.util.List;
public record PendingSummaryResponse(long openSanitationComplaints,long openReplenishmentRequests,long waterIssuesPendingAction,
 List<LowStockMedicine> lowStockMedicines,List<PendingItem> recentPending) {
 public record LowStockMedicine(Long id,String name,Integer quantity,Integer reorderLevel,String healthCentreName) {}
}
