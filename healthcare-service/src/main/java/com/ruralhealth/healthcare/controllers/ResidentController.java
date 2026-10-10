
package com.ruralhealth.healthcare.controllers;

import com.ruralhealth.healthcare.dto.ResidentForm;
import com.ruralhealth.healthcare.entity.Resident;
import com.ruralhealth.healthcare.service.ResidentService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResidentController {

    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    // 1. List all residents
    @GetMapping("/residents")
    public String listResidents(Model model) {

        model.addAttribute("residents", residentService.getAllResidents());

        return "residents/list";
    }

    // 2. Display the creation form
    @GetMapping("/residents/new")
    public String showCreateForm(Model model) {

        model.addAttribute("resident", new ResidentForm());

        return "residents/form";
    }

    // 3. Process the creation form
    @PostMapping("/residents")
    public String createResident(@Valid @ModelAttribute("resident") ResidentForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "residents/form";
        }

        Resident savedResident = residentService.createResident(form);

        redirectAttributes.addFlashAttribute("successMessage", "Resident created successfully.");

        return "redirect:/residents/" + savedResident.getId();
    }

    // 4. Display resident details and history
    @GetMapping("/residents/{id}")
    public String showResidentDetails(@PathVariable Long id, Model model) {

        Resident resident = residentService.getResidentById(id);

        model.addAttribute("resident", resident);
        model.addAttribute("appointments", residentService.getAppointmentsForResident(id));
        model.addAttribute("consultations", residentService.getConsultationsForResident(id));

        return "residents/detail";
    }
}
