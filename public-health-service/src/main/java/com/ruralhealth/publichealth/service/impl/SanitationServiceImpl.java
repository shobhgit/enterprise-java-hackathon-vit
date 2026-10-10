package com.ruralhealth.publichealth.service.impl;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.*;
import com.ruralhealth.publichealth.entity.enums.*;
import com.ruralhealth.publichealth.exception.*;
import com.ruralhealth.publichealth.repository.*;
import com.ruralhealth.publichealth.service.SanitationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class SanitationServiceImpl implements SanitationService {
 private final SanitationFacilityRepository facilities; private final MaintenanceRecordRepository maintenance; private final SanitationComplaintRepository complaints;
 public SanitationServiceImpl(SanitationFacilityRepository f,MaintenanceRecordRepository m,SanitationComplaintRepository c){facilities=f;maintenance=m;complaints=c;}
 private SanitationFacility findFacility(Long id){return facilities.findById(id).orElseThrow(()->new ResourceNotFoundException("Facility not found: "+id));}
 private SanitationComplaint findComplaint(Long id){return complaints.findById(id).orElseThrow(()->new ResourceNotFoundException("Complaint not found: "+id));}
 public List<FacilityResponse> facilities(){return facilities.findAll().stream().map(FacilityResponse::from).toList();}
 public FacilityResponse facility(Long id){return FacilityResponse.from(findFacility(id));}
 public FacilityResponse createFacility(FacilityRequest r){SanitationFacility f=new SanitationFacility();f.setName(r.getName());f.setType(r.getType());f.setLocation(r.getLocation());f.setCleanlinessStatus(r.getCleanlinessStatus());return FacilityResponse.from(facilities.save(f));}
 public FacilityResponse cleanliness(Long id,CleanlinessStatus s){SanitationFacility f=findFacility(id);f.setCleanlinessStatus(s);return FacilityResponse.from(facilities.save(f));}
 @Transactional public MaintenanceResponse addMaintenance(Long id,MaintenanceRequest r){SanitationFacility f=findFacility(id);MaintenanceRecord m=new MaintenanceRecord();m.setFacilityId(id);m.setPerformedOn(r.getPerformedOn());m.setDescription(r.getDescription());m.setPerformedBy(r.getPerformedBy());f.setLastMaintenanceDate(r.getPerformedOn());facilities.save(f);return MaintenanceResponse.from(maintenance.save(m));}
 public List<MaintenanceResponse> maintenance(Long id){findFacility(id);return maintenance.findByFacilityIdOrderByPerformedOnDesc(id).stream().map(MaintenanceResponse::from).toList();}
 public List<ComplaintResponse> complaints(WorkStatus s){List<SanitationComplaint> xs=s==null?complaints.findAllByOrderByCreatedAtDesc():complaints.findByStatus(s);return xs.stream().map(ComplaintResponse::from).toList();}
 public ComplaintResponse complaint(Long id){return ComplaintResponse.from(findComplaint(id));}
 public ComplaintResponse createComplaint(ComplaintRequest r){SanitationFacility f=findFacility(r.getFacilityId());SanitationComplaint c=new SanitationComplaint();c.setFacilityId(f.getId());c.setFacilityName(f.getName());c.setDescription(r.getDescription());c.setReportedBy(r.getReportedBy());c.setStatus(WorkStatus.OPEN);return ComplaintResponse.from(complaints.save(c));}
 public ComplaintResponse updateComplaint(Long id,ComplaintStatusRequest r){SanitationComplaint c=findComplaint(id);if(rank(r.getStatus())<rank(c.getStatus()))throw new BusinessRuleException("Status cannot move backwards");if(r.getStatus()==WorkStatus.RESOLVED&&(r.getResolution()==null||r.getResolution().isBlank()))throw new IllegalArgumentException("resolution is required when status is RESOLVED");c.setStatus(r.getStatus());if(r.getResolution()!=null)c.setResolution(r.getResolution());return ComplaintResponse.from(complaints.save(c));}
 private int rank(WorkStatus s){return switch(s){case OPEN->0;case IN_PROGRESS->1;case RESOLVED->2;};}
}
