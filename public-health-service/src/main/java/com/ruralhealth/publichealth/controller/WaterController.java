package com.ruralhealth.publichealth.controller;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.enums.WaterActionStatus;
import com.ruralhealth.publichealth.service.WaterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/water-records")
public class WaterController {
 private final WaterService service;
 public WaterController(WaterService s){service=s;}
 @GetMapping public List<WaterRecordResponse> all(@RequestParam(required=false) WaterActionStatus actionStatus){return service.all(actionStatus);}
 @GetMapping("/{id}") public WaterRecordResponse one(@PathVariable Long id){return service.one(id);}
 @PostMapping public ResponseEntity<WaterRecordResponse> create(@Valid @RequestBody WaterRecordRequest r){return ResponseEntity.status(201).body(service.create(r));}
 @PatchMapping("/{id}/action-status") public WaterRecordResponse update(@PathVariable Long id,@Valid @RequestBody WaterActionRequest r){return service.update(id,r);}
}
