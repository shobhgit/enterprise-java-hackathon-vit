package com.ruralhealth.healthcare.controllers;

import com.ruralhealth.healthcare.client.PublicHealthClient;
import com.ruralhealth.healthcare.service.HealthCentreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PublicHealthPageController {
    private final PublicHealthClient publicHealth;
    private final HealthCentreService centres;

    public PublicHealthPageController(PublicHealthClient publicHealth, HealthCentreService centres) {
        this.publicHealth = publicHealth;
        this.centres = centres;
    }

    @GetMapping("/medicines")
    public String medicines(Model model) {
        var medicines = publicHealth.getList("/api/medicines");
        model.addAttribute("medicines", medicines);
        model.addAttribute("lowStockCount", medicines.stream()
                .filter(medicine -> Boolean.TRUE.equals(medicine.get("lowStock"))).count());
        return "medicines/list";
    }

    @GetMapping("/medicines/new")
    public String newMedicine(Model model) {
        model.addAttribute("medicine", new HashMap<String, Object>());
        model.addAttribute("centres", centres.getAllCentres());
        return "medicines/form";
    }

    @PostMapping("/medicines")
    public String createMedicine(@RequestParam Map<String, String> form, RedirectAttributes redirect) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", form.get("name"));
        payload.put("type", form.get("type"));
        payload.put("unit", form.get("unit"));
        payload.put("quantity", Integer.valueOf(form.get("quantity")));
        payload.put("reorderLevel", Integer.valueOf(form.get("reorderLevel")));
        payload.put("healthCentreId", Long.valueOf(form.get("healthCentreId")));
        payload.put("healthCentreName", centres.getAllCentres().stream()
                .filter(c -> c.getId().equals(Long.valueOf(form.get("healthCentreId"))))
                .map(c -> c.getName()).findFirst().orElseThrow());
        publicHealth.post("/api/medicines", payload);
        redirect.addFlashAttribute("successMessage", "Medicine added to inventory.");
        return "redirect:/medicines";
    }

    @GetMapping("/medicines/{id}")
    public String medicine(@PathVariable Long id, Model model) {
        model.addAttribute("medicine", publicHealth.get("/api/medicines/" + id));
        model.addAttribute("issues", publicHealth.getList("/api/medicines/" + id + "/issues"));
        return "medicines/detail";
    }

    @PostMapping("/medicines/{id}/issue")
    public String issueMedicine(@PathVariable Long id, @RequestParam Integer quantity,
                                @RequestParam String issuedTo, @RequestParam(required = false) String reason,
                                @RequestParam LocalDate issuedOn, RedirectAttributes redirect) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("quantity", quantity);
        payload.put("issuedTo", issuedTo);
        payload.put("reason", reason);
        payload.put("issuedOn", issuedOn.toString());
        publicHealth.post("/api/medicines/" + id + "/issues", payload);
        redirect.addFlashAttribute("successMessage", "Medicine issue recorded and stock updated.");
        return "redirect:/medicines/" + id;
    }

    @PostMapping("/medicines/{id}/restock")
    public String restockMedicine(@PathVariable Long id, @RequestParam Integer quantity,
                                  @RequestParam(required = false) String note, RedirectAttributes redirect) {
        publicHealth.post("/api/medicines/" + id + "/restock", Map.of("quantity", quantity, "note", note == null ? "" : note));
        redirect.addFlashAttribute("successMessage", "Stock level updated.");
        return "redirect:/medicines/" + id;
    }

    @GetMapping("/replenishments")
    public String replenishments(Model model) {
        model.addAttribute("requests", publicHealth.getList("/api/replenishment-requests"));
        return "medicines/replenishment-list";
    }

    @GetMapping("/replenishments/new")
    public String newReplenishment(Model model) {
        model.addAttribute("medicines", publicHealth.getList("/api/medicines"));
        return "medicines/replenishment-form";
    }

    @PostMapping("/replenishments")
    public String createReplenishment(@RequestParam Long medicineId, @RequestParam Integer quantity,
                                      @RequestParam String reason, java.security.Principal principal,
                                      RedirectAttributes redirect) {
        publicHealth.post("/api/replenishment-requests", Map.of(
                "medicineId", medicineId,
                "quantityRequested", quantity,
                "requestedBy", principal.getName(),
                "remarks", reason));
        redirect.addFlashAttribute("successMessage", "Replenishment request submitted.");
        return "redirect:/replenishments";
    }

    @PostMapping("/replenishments/{id}/status")
    public String updateReplenishment(@PathVariable Long id, @RequestParam String status,
                                      RedirectAttributes redirect) {
        publicHealth.patch("/api/replenishment-requests/" + id + "/status", Map.of("status", status));
        redirect.addFlashAttribute("successMessage", "Replenishment request updated.");
        return "redirect:/replenishments";
    }

    @GetMapping("/sanitation/facilities")
    public String facilities(Model model) {
        model.addAttribute("facilities", publicHealth.getList("/api/sanitation/facilities"));
        return "sanitation/facility-list";
    }

    @GetMapping("/sanitation/facilities/new")
    public String newFacility() { return "sanitation/facility-form"; }

    @PostMapping("/sanitation/facilities")
    public String createFacility(@RequestParam String name, @RequestParam String type,
                                 @RequestParam String location, @RequestParam String cleanlinessStatus,
                                 RedirectAttributes redirect) {
        publicHealth.post("/api/sanitation/facilities", Map.of(
                "name", name, "type", type, "location", location, "cleanlinessStatus", cleanlinessStatus));
        redirect.addFlashAttribute("successMessage", "Sanitation facility added.");
        return "redirect:/sanitation/facilities";
    }

    @GetMapping("/sanitation/facilities/{id}")
    public String facility(@PathVariable Long id, Model model) {
        model.addAttribute("facility", publicHealth.get("/api/sanitation/facilities/" + id));
        model.addAttribute("maintenanceRecords", publicHealth.getList("/api/sanitation/facilities/" + id + "/maintenance"));
        return "sanitation/facility-detail";
    }

    @PostMapping("/sanitation/facilities/{id}/cleanliness")
    public String updateFacilityCleanliness(@PathVariable Long id, @RequestParam String cleanlinessStatus,
                                           RedirectAttributes redirect) {
        publicHealth.patch("/api/sanitation/facilities/" + id + "/cleanliness", Map.of("cleanlinessStatus", cleanlinessStatus));
        redirect.addFlashAttribute("successMessage", "Facility condition updated.");
        return "redirect:/sanitation/facilities/" + id;
    }

    @PostMapping("/sanitation/facilities/{id}/maintenance")
    public String recordMaintenance(@PathVariable Long id, @RequestParam LocalDate performedOn,
                                    @RequestParam String description, java.security.Principal principal,
                                    RedirectAttributes redirect) {
        publicHealth.post("/api/sanitation/facilities/" + id + "/maintenance", Map.of(
                "performedOn", performedOn.toString(), "description", description,
                "performedBy", principal.getName()));
        redirect.addFlashAttribute("successMessage", "Maintenance activity recorded.");
        return "redirect:/sanitation/facilities/" + id;
    }

    @GetMapping("/sanitation/complaints")
    public String complaints(Model model) {
        model.addAttribute("complaints", publicHealth.getList("/api/sanitation/complaints"));
        return "sanitation/complaint-list";
    }

    @GetMapping("/sanitation/complaints/new")
    public String newComplaint(Model model) {
        model.addAttribute("facilities", publicHealth.getList("/api/sanitation/facilities"));
        return "sanitation/complaint-form";
    }

    @PostMapping("/sanitation/complaints")
    public String createComplaint(@RequestParam Long facilityId, @RequestParam String description,
                                  @RequestParam String reportedBy, RedirectAttributes redirect) {
        publicHealth.post("/api/sanitation/complaints", Map.of(
                "facilityId", facilityId, "description", description, "reportedBy", reportedBy));
        redirect.addFlashAttribute("successMessage", "Sanitation complaint reported.");
        return "redirect:/sanitation/complaints";
    }

    @PostMapping("/sanitation/complaints/{id}/status")
    public String updateComplaint(@PathVariable Long id, @RequestParam String status,
                                  @RequestParam(required = false) String resolution,
                                  RedirectAttributes redirect) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("status", status);
        payload.put("resolution", resolution);
        publicHealth.patch("/api/sanitation/complaints/" + id + "/status", payload);
        redirect.addFlashAttribute("successMessage", "Complaint status updated.");
        return "redirect:/sanitation/complaints";
    }

    @GetMapping("/water")
    public String water(Model model) {
        model.addAttribute("records", publicHealth.getList("/api/water-records"));
        return "water/list";
    }

    @GetMapping("/water/new")
    public String newWaterRecord() { return "water/form"; }

    @PostMapping("/water")
    public String createWaterRecord(@RequestParam String location, @RequestParam String source,
                                    @RequestParam(required = false) String supplyInfo,
                                    @RequestParam LocalDate observedOn, @RequestParam String qualityStatus,
                                    @RequestParam(required = false) String issueDescription,
                                    @RequestParam String reportedBy, RedirectAttributes redirect) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("location", location);
        payload.put("source", source);
        payload.put("supplyInfo", supplyInfo);
        payload.put("observedOn", observedOn.toString());
        payload.put("qualityStatus", qualityStatus);
        payload.put("issueDescription", issueDescription);
        payload.put("reportedBy", reportedBy);
        publicHealth.post("/api/water-records", payload);
        redirect.addFlashAttribute("successMessage", "Water observation recorded.");
        return "redirect:/water";
    }

    @PostMapping("/water/{id}/status")
    public String updateWaterStatus(@PathVariable Long id, @RequestParam String actionStatus,
                                    @RequestParam(required = false) String correctiveAction,
                                    RedirectAttributes redirect) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("actionStatus", actionStatus);
        payload.put("correctiveAction", correctiveAction);
        publicHealth.patch("/api/water-records/" + id + "/action-status", payload);
        redirect.addFlashAttribute("successMessage", "Water response status updated.");
        return "redirect:/water";
    }

    @ExceptionHandler(RestClientException.class)
    public String publicHealthError(RestClientException exception, Model model) {
        model.addAttribute("errorMessage", "Public Health could not complete this request. Check that its API is running and the shared key is configured.");
        return "error";
    }
}
