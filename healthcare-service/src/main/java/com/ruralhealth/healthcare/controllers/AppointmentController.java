
package com.ruralhealth.healthcare.controller;

import com.ruralhealth.healthcare.dto.AppointmentForm;
import com.ruralhealth.healthcare.dto.ConsultationForm;
import com.ruralhealth.healthcare.entity.Appointment;
import com.ruralhealth.healthcare.entity.enums.AppointmentStatus;
import com.ruralhealth.healthcare.repository.HealthWorkerRepository;
import com.ruralhealth.healthcare.service.AppointmentService;
import com.ruralhealth.healthcare.service.HealthCentreService;
import com.ruralhealth.healthcare.service.ResidentService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ResidentService residentService;
    private final HealthCentreService healthCentreService;
    private final HealthWorkerRepository healthWorkerRepository;

    public AppointmentController(
            AppointmentService appointmentService,
            ResidentService residentService,
            HealthCentreService healthCentreService,
            HealthWorkerRepository healthWorkerRepository) {

        this.appointmentService = appointmentService;
        this.residentService = residentService;
        this.healthCentreService = healthCentreService;
        this.healthWorkerRepository = healthWorkerRepository;
    }

    @GetMapping("/appointments")
    public String listAppointments(@RequestParam(required = false) AppointmentStatus status, Model model) {
        model.addAttribute(
                "appointments",
                status == null ? appointmentService.getAllAppointments() : appointmentService.getAppointmentsByStatus(status)
        );

        return "appointments/list";
    }

    @GetMapping("/appointments/new")
    public String showCreateForm(Model model) {
        model.addAttribute("appointment", new AppointmentForm());
        populateFormOptions(model);

        return "appointments/form";
    }

    @PostMapping("/appointments")
    public String createAppointment(
            @Valid @ModelAttribute("appointment") AppointmentForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            populateFormOptions(model);
            return "appointments/form";
        }

        Appointment savedAppointment = appointmentService.createAppointment(form);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Appointment created successfully."
        );

        return "redirect:/appointments/" + savedAppointment.getId();
    }

    @GetMapping("/appointments/{id}")
    public String showAppointmentDetails(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "appointment",
                appointmentService.getAppointmentById(id)
        );
        model.addAttribute("consultation", appointmentService.getConsultationForAppointment(id));

        return "appointments/detail";
    }

    @PostMapping("/appointments/{id}/status")
    public String updateAppointmentStatus(@PathVariable Long id, @RequestParam AppointmentStatus status,
                                          RedirectAttributes redirectAttributes) {
        appointmentService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Appointment cancelled.");
        return "redirect:/appointments/" + id;
    }

    @GetMapping("/appointments/{id}/consultation")
    public String showConsultationForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "appointment",
                appointmentService.getAppointmentById(id)
        );
        model.addAttribute("consultation", new ConsultationForm());

        return "appointments/consultation-form";
    }

    @PostMapping("/appointments/{id}/consultation")
    public String createConsultation(
            @PathVariable Long id,
            @Valid @ModelAttribute("consultation")
            ConsultationForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "appointment",
                    appointmentService.getAppointmentById(id)
            );

            return "appointments/consultation-form";
        }

        appointmentService.createConsultation(id, form);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Consultation recorded successfully."
        );

        return "redirect:/appointments/" + id;
    }

    private void populateFormOptions(Model model) {
        model.addAttribute(
                "residents",
                residentService.getAllResidents()
        );

        model.addAttribute(
                "centres",
                healthCentreService.getAllCentres()
        );

        model.addAttribute(
                "workers",
                healthWorkerRepository.findAll()
        );
    }
}
