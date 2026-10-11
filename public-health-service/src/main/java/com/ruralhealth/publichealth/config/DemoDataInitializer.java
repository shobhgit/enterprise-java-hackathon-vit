package com.ruralhealth.publichealth.config;

import com.ruralhealth.publichealth.entity.*;
import com.ruralhealth.publichealth.entity.enums.*;
import com.ruralhealth.publichealth.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DemoDataInitializer {
    @Bean
    CommandLineRunner initializePublicHealthDemoData(
            MedicineRepository medicineRepository,
            MedicineIssueRepository issueRepository,
            ReplenishmentRequestRepository requestRepository,
            SanitationFacilityRepository facilityRepository,
            MaintenanceRecordRepository maintenanceRepository,
            SanitationComplaintRepository complaintRepository,
            WaterRecordRepository waterRepository) {
        return args -> {
            // Top up each table independently. Existing records are retained; restart is safe.
            var medicines = medicineRepository.findAll();
            Medicine[] catalog = {
                    medicine("Paracetamol 500 mg", MedicineType.MEDICINE, "tablets", 148, 40, 1L, "Kaveri Rural Health Centre"),
                    medicine("ORS sachets", MedicineType.SUPPLY, "sachets", 18, 30, 1L, "Kaveri Rural Health Centre"),
                    medicine("Iron + folic acid", MedicineType.MEDICINE, "strips", 22, 25, 2L, "Malar Primary Health Centre"),
                    medicine("Water purification tablets", MedicineType.SUPPLY, "tablets", 210, 50, 2L, "Malar Primary Health Centre"),
                    medicine("Amoxicillin 250 mg", MedicineType.MEDICINE, "capsules", 16, 25, 1L, "Kaveri Rural Health Centre"),
                    medicine("Zinc 20 mg", MedicineType.MEDICINE, "tablets", 75, 30, 1L, "Kaveri Rural Health Centre"),
                    medicine("Antimalarial tablets", MedicineType.MEDICINE, "courses", 12, 20, 2L, "Malar Primary Health Centre"),
                    medicine("Bandage rolls", MedicineType.SUPPLY, "rolls", 44, 15, 2L, "Malar Primary Health Centre"),
                    medicine("Disposable gloves", MedicineType.SUPPLY, "boxes", 8, 12, 1L, "Kaveri Rural Health Centre"),
                    medicine("Antiseptic solution", MedicineType.SUPPLY, "bottles", 31, 10, 2L, "Malar Primary Health Centre"),
                    medicine("Salbutamol inhalers", MedicineType.MEDICINE, "inhalers", 19, 8, 1L, "Kaveri Rural Health Centre"),
                    medicine("Vitamin A drops", MedicineType.MEDICINE, "bottles", 14, 18, 2L, "Malar Primary Health Centre")
            };
            for (Medicine candidate : catalog) {
                if (medicines.size() >= 12) break;
                if (medicines.stream().noneMatch(m -> m.getName().equalsIgnoreCase(candidate.getName())))
                    medicines.add(medicineRepository.save(candidate));
            }
            medicines = medicineRepository.findAll();
            while (medicines.size() < 12) {
                int n = medicines.size() + 1;
                medicines.add(medicineRepository.save(medicine("Demo medicine " + n, MedicineType.MEDICINE,
                        "packs", 30 + n, 15, n % 2 == 0 ? 2L : 1L,
                        n % 2 == 0 ? "Malar Primary Health Centre" : "Kaveri Rural Health Centre")));
            }

            var issues = issueRepository.findAll();
            while (issues.size() < 3) {
                Medicine m = medicines.get(issues.size() % medicines.size());
                MedicineIssue issue = new MedicineIssue();
                issue.setMedicineId(m.getId()); issue.setQuantity(5 + issues.size());
                issue.setIssuedTo("Village outreach clinic " + (issues.size() + 1));
                issue.setReason("Routine community clinic stock");
                issue.setIssuedOn(LocalDate.now().minusDays(issues.size() + 1));
                issue.setRemainingQuantity(m.getQuantity());
                issues.add(issueRepository.save(issue));
            }
            var requests = requestRepository.findAll();
            while (requests.size() < 8) {
                Medicine m = medicines.get(requests.size() % medicines.size());
                requests.add(requestRepository.save(request(m, 20 + requests.size() * 5,
                        requests.size() % 2 == 0 ? "healthworker" : "officer",
                        "Demo replenishment request for " + m.getName(),
                        requests.size() % 3 == 0 ? WorkStatus.OPEN : WorkStatus.IN_PROGRESS)));
            }

            var facilities = facilityRepository.findAll();
            FacilityType[] facilityTypes = FacilityType.values();
            while (facilities.size() < 8) {
                int n = facilities.size() + 1;
                facilities.add(facilityRepository.save(facility("Community facility " + n,
                        facilityTypes[n % facilityTypes.length], "Village sector " + n,
                        n % 3 == 0 ? CleanlinessStatus.DAMAGED : CleanlinessStatus.CLEAN)));
            }
            var maintenance = maintenanceRepository.findAll();
            while (maintenance.size() < 3) {
                int n = maintenance.size(); MaintenanceRecord record = new MaintenanceRecord();
                record.setFacilityId(facilities.get(n % facilities.size()).getId());
                record.setPerformedOn(LocalDate.now().minusDays(5 + n));
                record.setDescription("Routine facility inspection and minor repairs");
                record.setPerformedBy("Village sanitation team"); maintenance.add(maintenanceRepository.save(record));
            }
            var complaints = complaintRepository.findAll();
            while (complaints.size() < 8) {
                int n = complaints.size(); SanitationFacility f = facilities.get(n % facilities.size());
                complaints.add(complaintRepository.save(complaint(f,
                        "Demo report: " + (n % 2 == 0 ? "cleaning required" : "minor maintenance required"),
                        "Resident " + (n + 1), n % 3 == 0 ? WorkStatus.OPEN : WorkStatus.IN_PROGRESS)));
            }
            var observations = waterRepository.findAll();
            WaterSource[] sources = WaterSource.values(); WaterQuality[] qualities = WaterQuality.values();
            while (observations.size() < 8) {
                int n = observations.size();
                WaterRecord record = water("Village water point " + (n + 1), sources[n % sources.length],
                        "Community supply point", LocalDate.now().minusDays(n), qualities[n % qualities.length],
                        n % 3 == 0 ? "Routine follow-up sample required" : null,
                        "Health worker " + (n + 1), n % 3 == 0 ? WaterActionStatus.OPEN : WaterActionStatus.NOT_REQUIRED);
                observations.add(waterRepository.save(record));
            }
        };
    }

    private Medicine medicine(String name, MedicineType type, String unit, int quantity,
                              int reorderLevel, long centreId, String centreName) {
        Medicine value = new Medicine();
        value.setName(name);
        value.setType(type);
        value.setUnit(unit);
        value.setQuantity(quantity);
        value.setReorderLevel(reorderLevel);
        value.setHealthCentreId(centreId);
        value.setHealthCentreName(centreName);
        return value;
    }

    private ReplenishmentRequest request(Medicine medicine, int quantity, String requestedBy,
                                         String remarks, WorkStatus status) {
        ReplenishmentRequest value = new ReplenishmentRequest();
        value.setMedicineId(medicine.getId());
        value.setMedicineName(medicine.getName());
        value.setQuantityRequested(quantity);
        value.setRequestedBy(requestedBy);
        value.setRemarks(remarks);
        value.setHealthCentreId(medicine.getHealthCentreId());
        value.setHealthCentreName(medicine.getHealthCentreName());
        value.setStatus(status);
        return value;
    }

    private SanitationFacility facility(String name, FacilityType type, String location,
                                        CleanlinessStatus status) {
        SanitationFacility value = new SanitationFacility();
        value.setName(name);
        value.setType(type);
        value.setLocation(location);
        value.setCleanlinessStatus(status);
        return value;
    }

    private SanitationComplaint complaint(SanitationFacility facility, String description,
                                          String reportedBy, WorkStatus status) {
        SanitationComplaint value = new SanitationComplaint();
        value.setFacilityId(facility.getId());
        value.setFacilityName(facility.getName());
        value.setDescription(description);
        value.setReportedBy(reportedBy);
        value.setStatus(status);
        return value;
    }

    private WaterRecord water(String location, WaterSource source, String supplyInfo,
                              LocalDate observedOn, WaterQuality quality, String issue,
                              String reportedBy, WaterActionStatus status) {
        WaterRecord value = new WaterRecord();
        value.setLocation(location);
        value.setSource(source);
        value.setSupplyInfo(supplyInfo);
        value.setObservedOn(observedOn);
        value.setQualityStatus(quality);
        value.setIssueDescription(issue);
        value.setReportedBy(reportedBy);
        value.setActionStatus(status);
        return value;
    }
}
