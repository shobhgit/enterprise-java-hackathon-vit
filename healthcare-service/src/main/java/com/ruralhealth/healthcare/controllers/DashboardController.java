
package com.ruralhealth.healthcare.controllers;

import com.ruralhealth.healthcare.client.PublicHealthClient;
import com.ruralhealth.healthcare.entity.enums.CampStatus;
import com.ruralhealth.healthcare.repository.ResidentRepository;
import com.ruralhealth.healthcare.repository.AppointmentRepository;
import com.ruralhealth.healthcare.repository.HealthCampRepository;
import com.ruralhealth.healthcare.repository.CampBeneficiaryRepository;
import com.ruralhealth.healthcare.repository.HealthWorkerRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Map;

@Controller
public class DashboardController {

    private final PublicHealthClient publicHealthClient;
    private final ResidentRepository residentRepository;
    private final AppointmentRepository appointmentRepository;
    private final HealthCampRepository healthCampRepository;
    private final CampBeneficiaryRepository campBeneficiaryRepository;
    private final HealthWorkerRepository healthWorkerRepository;

    public DashboardController(
            PublicHealthClient publicHealthClient,
            ResidentRepository residentRepository,
            AppointmentRepository appointmentRepository,
            HealthCampRepository healthCampRepository,
            CampBeneficiaryRepository campBeneficiaryRepository,
            HealthWorkerRepository healthWorkerRepository) {

        this.publicHealthClient = publicHealthClient;
        this.residentRepository = residentRepository;
        this.appointmentRepository = appointmentRepository;
        this.healthCampRepository = healthCampRepository;
        this.campBeneficiaryRepository = campBeneficiaryRepository;
        this.healthWorkerRepository = healthWorkerRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        // Healthcare service metrics
        model.addAttribute(
                "totalResidents",
                residentRepository.count()
        );

        model.addAttribute(
                "totalAppointments",
                appointmentRepository.count()
        );

        model.addAttribute(
                "totalHealthWorkers",
                healthWorkerRepository.count()
        );

        model.addAttribute(
                "totalHealthCamps",
                healthCampRepository.count()
        );

        model.addAttribute(
                "upcomingHealthCamps",
                healthCampRepository
                        .countByStartDateGreaterThanEqualAndStatus(
                                LocalDate.now(),
                                CampStatus.PLANNED
                        )
        );

        model.addAttribute(
                "totalCampBeneficiaries",
                campBeneficiaryRepository.count()
        );

        // Public Health service summary
        Map<String, Object> pendingSummary =
                publicHealthClient.getPendingSummary();

        model.addAttribute("pendingSummary", pendingSummary);

        model.addAttribute(
                "openSanitationComplaints",
                pendingSummary.get("openSanitationComplaints")
        );

        model.addAttribute(
                "openReplenishmentRequests",
                pendingSummary.get("openReplenishmentRequests")
        );

        model.addAttribute(
                "waterIssuesPendingAction",
                pendingSummary.get("waterIssuesPendingAction")
        );

        model.addAttribute(
                "lowStockMedicines",
                pendingSummary.get("lowStockMedicines")
        );

        model.addAttribute(
                "recentPending",
                pendingSummary.get("recentPending")
        );

        return "dashboard";
    }
}
