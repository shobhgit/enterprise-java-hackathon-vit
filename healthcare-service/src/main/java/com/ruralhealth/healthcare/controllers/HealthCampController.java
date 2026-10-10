
package com.ruralhealth.healthcare.controllers;

import com.ruralhealth.healthcare.dto.HealthCampForm;
import com.ruralhealth.healthcare.entity.CampBeneficiary;
import com.ruralhealth.healthcare.entity.HealthCamp;
import com.ruralhealth.healthcare.entity.enums.CampStatus;
import com.ruralhealth.healthcare.entity.enums.FollowUpStatus;
import com.ruralhealth.healthcare.service.HealthCampService;
import com.ruralhealth.healthcare.service.ResidentService;
import com.ruralhealth.healthcare.service.HealthCentreService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HealthCampController {

    private final HealthCampService healthCampService;
    private final HealthCentreService healthCentreService;
    private final ResidentService residentService;

    public HealthCampController(
            HealthCampService healthCampService,
            HealthCentreService healthCentreService,
            ResidentService residentService) {

        this.healthCampService = healthCampService;
        this.healthCentreService = healthCentreService;
        this.residentService = residentService;
    }

    @GetMapping("/camps")
    public String listCamps(Model model) {
        model.addAttribute("camps", healthCampService.getAllCamps());
        return "camps/list";
    }

    @GetMapping("/camps/new")
    public String showCreateForm(Model model) {
        model.addAttribute("camp", new HealthCampForm());
        populateCentreOptions(model);
        return "camps/form";
    }

    @PostMapping("/camps")
    public String createCamp(
            @Valid @ModelAttribute("camp") HealthCampForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            populateCentreOptions(model);
            return "camps/form";
        }

        HealthCamp savedCamp = healthCampService.createCamp(form);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Health camp created successfully."
        );

        return "redirect:/camps/" + savedCamp.getId();
    }

    @GetMapping("/camps/{id}")
    public String showCampDetails(
            @PathVariable Long id,
            Model model) {

        HealthCamp camp = healthCampService.getCampById(id);

        model.addAttribute("camp", camp);
        model.addAttribute(
                "beneficiaries",
                healthCampService.getBeneficiaries(id)
        );
        model.addAttribute("residents", residentService.getAllResidents());

        return "camps/detail";
    }

    @PostMapping("/camps/{id}/beneficiaries")
    public String addBeneficiary(
            @PathVariable Long id,
            @RequestParam Long residentId,
            @RequestParam(required = false) String screeningResult,
            RedirectAttributes redirectAttributes) {

        healthCampService.addBeneficiary(
                id, residentId, screeningResult
        );

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Resident registered for the camp."
        );

        return "redirect:/camps/" + id;
    }

    @PostMapping("/camps/beneficiaries/{beneficiaryId}/follow-up")
    public String updateFollowUpStatus(
            @PathVariable Long beneficiaryId,
            @RequestParam FollowUpStatus status,
            @RequestParam Long campId,
            RedirectAttributes redirectAttributes) {

        healthCampService.updateFollowUpStatus(beneficiaryId, status);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Follow-up status updated."
        );

        return "redirect:/camps/" + campId;
    }

    @PostMapping("/camps/{id}/status")
    public String updateCampStatus(
            @PathVariable Long id,
            @RequestParam CampStatus status,
            RedirectAttributes redirectAttributes) {

        healthCampService.updateCampStatus(id, status);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Camp status updated."
        );

        return "redirect:/camps/" + id;
    }

    private void populateCentreOptions(Model model) {
        model.addAttribute(
                "centres",
                healthCentreService.getAllCentres()
        );
    }
}
