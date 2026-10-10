package com.ruralhealth.publichealth.controller;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.service.MedicineService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/medicines")
public class MedicineController {
 private final MedicineService service;
 public MedicineController(MedicineService s){service=s;}
 @GetMapping public List<MedicineResponse> all(@RequestParam(required=false) Long healthCentreId){return service.all(healthCentreId);}
 @GetMapping("/{id}") public MedicineResponse one(@PathVariable Long id){return service.one(id);}
 @PostMapping public ResponseEntity<MedicineResponse> create(@Valid @RequestBody MedicineRequest r){return ResponseEntity.status(201).body(service.create(r));}
 @GetMapping("/low-stock") public List<MedicineResponse> low(@RequestParam(required=false) Long healthCentreId){return service.lowStock(healthCentreId);}
 @PostMapping("/{id}/issues") public ResponseEntity<IssueResponse> issue(@PathVariable Long id,@Valid @RequestBody IssueRequest r){return ResponseEntity.status(201).body(service.issue(id,r));}
 @GetMapping("/{id}/issues") public List<IssueResponse> issues(@PathVariable Long id){return service.issues(id);}
 @PostMapping("/{id}/restock") public MedicineResponse restock(@PathVariable Long id,@Valid @RequestBody RestockRequest r){return service.restock(id,r);}
}
