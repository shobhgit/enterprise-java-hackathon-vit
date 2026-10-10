
package com.ruralhealth.healthcare.controllers;

import com.ruralhealth.healthcare.client.PublicHealthClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PublicHealthTestController {

    private final PublicHealthClient publicHealthClient;

    public PublicHealthTestController(PublicHealthClient publicHealthClient) {
        this.publicHealthClient = publicHealthClient;
    }

    @GetMapping("/test/public-health/medicines")
    public List<Map<String, Object>> medicines() {
        return publicHealthClient.getMedicines();
    }

    @GetMapping("/test/public-health/pending-summary")
    public Map<String, Object> pendingSummary() {
        return publicHealthClient.getPendingSummary();
    }
}
