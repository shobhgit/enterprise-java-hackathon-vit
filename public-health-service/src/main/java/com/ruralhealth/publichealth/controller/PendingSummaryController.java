package com.ruralhealth.publichealth.controller;
import com.ruralhealth.publichealth.dto.PendingSummaryResponse;
import com.ruralhealth.publichealth.service.PendingSummaryService;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class PendingSummaryController {
 private final PendingSummaryService service;
 public PendingSummaryController(PendingSummaryService s){service=s;}
 @GetMapping("/pending-summary") public PendingSummaryResponse summary(){return service.get();}
}
