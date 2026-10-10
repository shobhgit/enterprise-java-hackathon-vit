package com.ruralhealth.publichealth.service.impl;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.WaterRecord;
import com.ruralhealth.publichealth.entity.enums.*;
import com.ruralhealth.publichealth.exception.*;
import com.ruralhealth.publichealth.repository.WaterRecordRepository;
import com.ruralhealth.publichealth.service.WaterService;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class WaterServiceImpl implements WaterService {
 private final WaterRecordRepository repo;
 public WaterServiceImpl(WaterRecordRepository r){repo=r;}
 private WaterRecord find(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Water record not found: "+id));}
 public List<WaterRecordResponse> all(WaterActionStatus s){List<WaterRecord> xs=s==null?repo.findAllByOrderByCreatedAtDesc():repo.findByActionStatus(s);return xs.stream().map(WaterRecordResponse::from).toList();}
 public WaterRecordResponse one(Long id){return WaterRecordResponse.from(find(id));}
 public WaterRecordResponse create(WaterRecordRequest r){WaterRecord w=new WaterRecord();w.setLocation(r.getLocation());w.setSource(r.getSource());w.setSupplyInfo(r.getSupplyInfo());w.setObservedOn(r.getObservedOn());w.setQualityStatus(r.getQualityStatus());w.setIssueDescription(r.getIssueDescription());w.setReportedBy(r.getReportedBy());w.setActionStatus(r.getQualityStatus()==WaterQuality.SAFE&&(r.getIssueDescription()==null||r.getIssueDescription().isBlank())?WaterActionStatus.NOT_REQUIRED:WaterActionStatus.OPEN);return WaterRecordResponse.from(repo.save(w));}
 public WaterRecordResponse update(Long id,WaterActionRequest r){WaterRecord w=find(id);WaterActionStatus current=w.getActionStatus(),next=r.getActionStatus();if(current==WaterActionStatus.NOT_REQUIRED&&next!=WaterActionStatus.NOT_REQUIRED){}else if(rank(next)<rank(current))throw new BusinessRuleException("Action status cannot move backwards");if((next==WaterActionStatus.IN_PROGRESS||next==WaterActionStatus.RESOLVED)&&(r.getCorrectiveAction()==null||r.getCorrectiveAction().isBlank()))throw new IllegalArgumentException("correctiveAction is required for this status");w.setActionStatus(next);if(r.getCorrectiveAction()!=null)w.setCorrectiveAction(r.getCorrectiveAction());return WaterRecordResponse.from(repo.save(w));}
 private int rank(WaterActionStatus s){return switch(s){case NOT_REQUIRED->0;case OPEN->1;case IN_PROGRESS->2;case RESOLVED->3;};}
}
