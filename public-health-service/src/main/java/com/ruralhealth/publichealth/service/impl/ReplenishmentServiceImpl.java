package com.ruralhealth.publichealth.service.impl;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.*;
import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import com.ruralhealth.publichealth.exception.*;
import com.ruralhealth.publichealth.repository.*;
import com.ruralhealth.publichealth.service.ReplenishmentService;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ReplenishmentServiceImpl implements ReplenishmentService {
 private final ReplenishmentRequestRepository requests; private final MedicineRepository medicines;
 public ReplenishmentServiceImpl(ReplenishmentRequestRepository r,MedicineRepository m){requests=r;medicines=m;}
 private ReplenishmentRequest find(Long id){return requests.findById(id).orElseThrow(()->new ResourceNotFoundException("Replenishment request not found: "+id));}
 public List<ReplenishmentResponse> all(WorkStatus s,Long c){List<ReplenishmentRequest> xs;if(s!=null&&c!=null)xs=requests.findByStatusAndHealthCentreId(s,c);else if(s!=null)xs=requests.findByStatus(s);else if(c!=null)xs=requests.findByHealthCentreId(c);else xs=requests.findAll();return xs.stream().map(ReplenishmentResponse::from).toList();}
 public ReplenishmentResponse one(Long id){return ReplenishmentResponse.from(find(id));}
 public ReplenishmentResponse create(ReplenishmentCreateRequest r){Medicine m=medicines.findById(r.getMedicineId()).orElseThrow(()->new ResourceNotFoundException("Medicine not found: "+r.getMedicineId()));ReplenishmentRequest x=new ReplenishmentRequest();x.setMedicineId(m.getId());x.setMedicineName(m.getName());x.setHealthCentreId(m.getHealthCentreId());x.setHealthCentreName(m.getHealthCentreName());x.setQuantityRequested(r.getQuantityRequested());x.setRequestedBy(r.getRequestedBy());x.setRemarks(r.getRemarks());x.setStatus(WorkStatus.OPEN);return ReplenishmentResponse.from(requests.save(x));}
 public ReplenishmentResponse update(Long id,WorkStatus status){ReplenishmentRequest x=find(id);int next=rank(status),current=rank(x.getStatus());if(next<current)throw new BusinessRuleException("Status cannot move backwards from "+x.getStatus()+" to "+status);x.setStatus(status);return ReplenishmentResponse.from(requests.save(x));}
 private int rank(WorkStatus s){return switch(s){case OPEN->0;case IN_PROGRESS->1;case RESOLVED->2;};}
}
