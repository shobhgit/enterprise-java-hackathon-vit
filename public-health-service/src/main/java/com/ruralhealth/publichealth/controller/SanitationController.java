package com.ruralhealth.publichealth.controller;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.enums.*;
import com.ruralhealth.publichealth.service.SanitationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/sanitation")
public class SanitationController {
 private final SanitationService service;
 public SanitationController(SanitationService s){service=s;}
 @GetMapping("/facilities") public List<FacilityResponse> facilities(){return service.facilities();}
 @GetMapping("/facilities/{id}") public FacilityResponse facility(@PathVariable Long id){return service.facility(id);}
 @PostMapping("/facilities") public ResponseEntity<FacilityResponse> createFacility(@Valid @RequestBody FacilityRequest r){return ResponseEntity.status(201).body(service.createFacility(r));}
 @PatchMapping("/facilities/{id}/cleanliness") public FacilityResponse cleanliness(@PathVariable Long id,@Valid @RequestBody CleanlinessRequest r){return service.cleanliness(id,r.getCleanlinessStatus());}
 @PostMapping("/facilities/{id}/maintenance") public ResponseEntity<MaintenanceResponse> maintenance(@PathVariable Long id,@Valid @RequestBody MaintenanceRequest r){return ResponseEntity.status(201).body(service.addMaintenance(id,r));}
 @GetMapping("/facilities/{id}/maintenance") public List<MaintenanceResponse> maintenanceHistory(@PathVariable Long id){return service.maintenance(id);}
 @GetMapping("/complaints") public List<ComplaintResponse> complaints(@RequestParam(required=false) WorkStatus status){return service.complaints(status);}
 @GetMapping("/complaints/{id}") public ComplaintResponse complaint(@PathVariable Long id){return service.complaint(id);}
 @PostMapping("/complaints") public ResponseEntity<ComplaintResponse> createComplaint(@Valid @RequestBody ComplaintRequest r){return ResponseEntity.status(201).body(service.createComplaint(r));}
 @PatchMapping("/complaints/{id}/status") public ComplaintResponse updateComplaint(@PathVariable Long id,@Valid @RequestBody ComplaintStatusRequest r){return service.updateComplaint(id,r);}
}
