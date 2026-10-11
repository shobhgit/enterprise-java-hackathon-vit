
package com.ruralhealth.healthcare.controllers;

import com.ruralhealth.healthcare.dto.HealthCentreForm;
import com.ruralhealth.healthcare.entity.HealthCentre;
import com.ruralhealth.healthcare.service.HealthCentreService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HealthCentreController {

    private final HealthCentreService healthCentreService;

    public HealthCentreController(
            HealthCentreService healthCentreService) {
        this.healthCentreService = healthCentreService;
    }

    @GetMapping("/centres")
    public String listCentres(Model model) {
        model.addAttribute(
                "centres",
                healthCentreService.getAllCentres()
        );

        return "centres/list";
    }

    @GetMapping("/centres/new")
    public String showCreateForm(Model model) {
        model.addAttribute("centre", new HealthCentreForm());

        return "centres/form";
    }

    @PostMapping("/centres")
    public String createCentre(
            @Valid @ModelAttribute("centre") HealthCentreForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "centres/form";
        }

        HealthCentre savedCentre =
                healthCentreService.createCentre(form);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Health centre created successfully."
        );

        return "redirect:/centres/" + savedCentre.getId();
    }

    @GetMapping("/centres/{id}")
    public String showCentreDetails(
            @PathVariable Long id,
            Model model) {

        HealthCentre centre =
                healthCentreService.getCentreById(id);

        model.addAttribute("centre", centre);

        return "centres/detail";
    }
}
