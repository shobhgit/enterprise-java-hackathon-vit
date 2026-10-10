package com.ruralhealth.publichealth.controller;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import com.ruralhealth.publichealth.service.ReplenishmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/replenishment-requests")
public class ReplenishmentController {
 private final ReplenishmentService service;
 public ReplenishmentController(ReplenishmentService s){service=s;}
 @GetMapping public List<ReplenishmentResponse> all(@RequestParam(required=false) WorkStatus status,@RequestParam(required=false) Long healthCentreId){return service.all(status,healthCentreId);}
 @GetMapping("/{id}") public ReplenishmentResponse one(@PathVariable Long id){return service.one(id);}
 @PostMapping public ResponseEntity<ReplenishmentResponse> create(@Valid @RequestBody ReplenishmentCreateRequest r){return ResponseEntity.status(201).body(service.create(r));}
 @PatchMapping("/{id}/status") public ReplenishmentResponse update(@PathVariable Long id,@Valid @RequestBody StatusUpdateRequest r){return service.update(id,r.getStatus());}
}
