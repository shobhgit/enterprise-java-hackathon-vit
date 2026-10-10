package com.ruralhealth.publichealth.service.impl;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.*;
import com.ruralhealth.publichealth.exception.*;
import com.ruralhealth.publichealth.repository.*;
import com.ruralhealth.publichealth.service.MedicineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class MedicineServiceImpl implements MedicineService {
 private final MedicineRepository medicines; private final MedicineIssueRepository issues;
 public MedicineServiceImpl(MedicineRepository m,MedicineIssueRepository i){medicines=m;issues=i;}
 private Medicine find(Long id){return medicines.findById(id).orElseThrow(()->new ResourceNotFoundException("Medicine not found: "+id));}
 public List<MedicineResponse> all(Long centreId){List<Medicine> xs=centreId==null?medicines.findAll():medicines.findByHealthCentreId(centreId);return xs.stream().map(MedicineResponse::from).toList();}
 public MedicineResponse one(Long id){return MedicineResponse.from(find(id));}
 public MedicineResponse create(MedicineRequest r){Medicine m=new Medicine();m.setName(r.getName());m.setType(r.getType());m.setUnit(r.getUnit());m.setQuantity(r.getQuantity());m.setReorderLevel(r.getReorderLevel());m.setHealthCentreId(r.getHealthCentreId());m.setHealthCentreName(r.getHealthCentreName());return MedicineResponse.from(medicines.save(m));}
 public List<MedicineResponse> lowStock(Long centreId){return all(centreId).stream().filter(MedicineResponse::lowStock).toList();}
 @Transactional public IssueResponse issue(Long id,IssueRequest r){Medicine m=find(id);if(m.getQuantity()<r.getQuantity())throw new BusinessRuleException("Not enough stock. Available: "+m.getQuantity());m.setQuantity(m.getQuantity()-r.getQuantity());medicines.save(m);MedicineIssue i=new MedicineIssue();i.setMedicineId(id);i.setQuantity(r.getQuantity());i.setIssuedTo(r.getIssuedTo());i.setReason(r.getReason());i.setIssuedOn(r.getIssuedOn());i.setRemainingQuantity(m.getQuantity());return IssueResponse.from(issues.save(i));}
 public List<IssueResponse> issues(Long id){find(id);return issues.findByMedicineIdOrderByIssuedOnDesc(id).stream().map(IssueResponse::from).toList();}
 @Transactional public MedicineResponse restock(Long id,RestockRequest r){Medicine m=find(id);try{m.setQuantity(Math.addExact(m.getQuantity(),r.getQuantity()));}catch(ArithmeticException e){throw new BusinessRuleException("Quantity is too large");}return MedicineResponse.from(medicines.save(m));}
}
