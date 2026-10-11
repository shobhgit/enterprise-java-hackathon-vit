
package com.ruralhealth.healthcare.controllers;

import com.ruralhealth.healthcare.client.PublicHealthClient;
import com.ruralhealth.healthcare.entity.enums.CampStatus;
import com.ruralhealth.healthcare.entity.enums.AppointmentStatus;
import com.ruralhealth.healthcare.entity.enums.FollowUpStatus;
import com.ruralhealth.healthcare.repository.HealthCentreRepository;
import com.ruralhealth.healthcare.repository.ResidentRepository;
import com.ruralhealth.healthcare.repository.AppointmentRepository;
import com.ruralhealth.healthcare.repository.HealthCampRepository;
import com.ruralhealth.healthcare.repository.CampBeneficiaryRepository;
import com.ruralhealth.healthcare.repository.HealthWorkerRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.Collections;

@Controller
public class DashboardController {

    private final PublicHealthClient publicHealthClient;
    private final ResidentRepository residentRepository;
    private final AppointmentRepository appointmentRepository;
    private final HealthCampRepository healthCampRepository;
    private final CampBeneficiaryRepository campBeneficiaryRepository;
    private final HealthWorkerRepository healthWorkerRepository;
    private final HealthCentreRepository healthCentreRepository;

    public DashboardController(
            PublicHealthClient publicHealthClient,
            ResidentRepository residentRepository,
            AppointmentRepository appointmentRepository,
            HealthCampRepository healthCampRepository,
            CampBeneficiaryRepository campBeneficiaryRepository,
            HealthWorkerRepository healthWorkerRepository,
            HealthCentreRepository healthCentreRepository) {

        this.publicHealthClient = publicHealthClient;
        this.residentRepository = residentRepository;
        this.appointmentRepository = appointmentRepository;
        this.healthCampRepository = healthCampRepository;
        this.campBeneficiaryRepository = campBeneficiaryRepository;
        this.healthWorkerRepository = healthWorkerRepository;
        this.healthCentreRepository = healthCentreRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        // Healthcare service metrics
        model.addAttribute(
                "totalResidents",
                residentRepository.count()
        );
        model.addAttribute("totalCentres", healthCentreRepository.count());

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

        model.addAttribute("upcomingAppointments", appointmentRepository
                .findByStatus(AppointmentStatus.SCHEDULED).stream()
                .filter(a -> !a.getScheduledAt().isBefore(java.time.LocalDateTime.now()))
                .sorted(java.util.Comparator.comparing(a -> a.getScheduledAt()))
                .limit(5).toList());
        model.addAttribute("upcomingCamps", healthCampRepository
                .findByStatusOrderByStartDateAsc(CampStatus.PLANNED).stream()
                .filter(c -> c.getStartDate() == null || !c.getStartDate().isBefore(LocalDate.now()))
                .limit(5).toList());
        model.addAttribute("pendingFollowUps", campBeneficiaryRepository
                .findByFollowUpStatus(FollowUpStatus.PENDING).stream().limit(5).toList());

        // Public Health service summary
        Map<String, Object> pendingSummary;
        try {
            pendingSummary = publicHealthClient.getPendingSummary();
            model.addAttribute("publicHealthAvailable", true);
        } catch (RestClientException exception) {
            pendingSummary = Map.of(
                    "openSanitationComplaints", 0,
                    "openReplenishmentRequests", 0,
                    "waterIssuesPendingAction", 0,
                    "lowStockMedicines", Collections.emptyList(),
                    "recentPending", Collections.emptyList());
            model.addAttribute("publicHealthAvailable", false);
        }

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
                pendingSummary.getOrDefault("lowStockMedicines", Collections.emptyList())
        );

        model.addAttribute(
                "recentPending",
                pendingSummary.getOrDefault("recentPending", Collections.emptyList())
        );

        return "dashboard";
    }
}
