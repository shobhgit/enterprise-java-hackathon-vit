package com.ruralhealth.publichealth.service;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.enums.*;
import com.ruralhealth.publichealth.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.*;
@Service
public class PendingSummaryService {
 private final MedicineRepository medicines; private final ReplenishmentRequestRepository replenishments; private final SanitationComplaintRepository complaints; private final WaterRecordRepository water;
 public PendingSummaryService(MedicineRepository m,ReplenishmentRequestRepository r,SanitationComplaintRepository c,WaterRecordRepository w){medicines=m;replenishments=r;complaints=c;water=w;}
 public PendingSummaryResponse get(){
  List<PendingItem> items=new ArrayList<>();
  complaints.findAllByOrderByCreatedAtDesc().stream().filter(x->x.getStatus()!=WorkStatus.RESOLVED).forEach(x->items.add(new PendingItem("SANITATION",x.getId(),x.getFacilityName()+": "+x.getDescription(),x.getStatus().name(),x.getCreatedAt())));
  replenishments.findAll().stream().filter(x->x.getStatus()!=WorkStatus.RESOLVED).forEach(x->items.add(new PendingItem("REPLENISHMENT",x.getId(),x.getMedicineName()+", "+x.getQuantityRequested()+" units",x.getStatus().name(),x.getCreatedAt())));
  water.findAllByOrderByCreatedAtDesc().stream().filter(x->x.getActionStatus()==WaterActionStatus.OPEN||x.getActionStatus()==WaterActionStatus.IN_PROGRESS).forEach(x->items.add(new PendingItem("WATER",x.getId(),x.getLocation()+": "+(x.getIssueDescription()==null?"water observation":x.getIssueDescription()),x.getActionStatus().name(),x.getCreatedAt())));
  items.sort(Comparator.comparing(PendingItem::createdAt,Comparator.nullsLast(Comparator.reverseOrder())));
  List<PendingSummaryResponse.LowStockMedicine> low=medicines.findAll().stream().filter(m->m.isLowStock()).map(m->new PendingSummaryResponse.LowStockMedicine(m.getId(),m.getName(),m.getQuantity(),m.getReorderLevel(),m.getHealthCentreName())).toList();
  return new PendingSummaryResponse(complaints.countByStatusIn(List.of(WorkStatus.OPEN,WorkStatus.IN_PROGRESS)),replenishments.countByStatusIn(List.of(WorkStatus.OPEN,WorkStatus.IN_PROGRESS)),water.countByActionStatusIn(List.of(WaterActionStatus.OPEN,WaterActionStatus.IN_PROGRESS)),low,items.stream().limit(10).toList());
 }
}
