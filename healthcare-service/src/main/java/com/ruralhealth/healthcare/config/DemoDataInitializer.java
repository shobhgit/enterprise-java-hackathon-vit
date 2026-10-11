package com.ruralhealth.healthcare.config;

import com.ruralhealth.healthcare.entity.*;
import com.ruralhealth.healthcare.entity.enums.*;
import com.ruralhealth.healthcare.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Adds fictitious demo records to the database without replacing existing data. */
@Configuration
public class DemoDataInitializer {
    @Bean
    CommandLineRunner initializeHealthcareDemoData(HealthCentreRepository centreRepo,
            HealthWorkerRepository workerRepo, ResidentRepository residentRepo,
            AppointmentRepository appointmentRepo, HealthCampRepository campRepo,
            CampBeneficiaryRepository beneficiaryRepo) {
        return args -> {
            List<HealthCentre> centres = centreRepo.findAll();
            String[] centreNames = {"Kaveri Rural Health Centre", "Malar Primary Health Centre", "Vaigai Community Clinic"};
            String[] centreLocations = {"Kaveripatti", "Malarpur", "Vaigai Nagar"};
            for (int i = centres.size(); i < 3; i++) {
                HealthCentre c = new HealthCentre(); c.setName(centreNames[i]); c.setLocation(centreLocations[i]);
                centres.add(centreRepo.save(c));
            }
            if (centres.isEmpty()) return;

            List<HealthWorker> workers = workerRepo.findAll();
            String[] workerNames = {"Anitha Rajan", "Selvan Kumar", "Meera Nair", "Arun Prakash", "Kavitha Devi", "Joseph Paul"};
            String[] designations = {"Community Health Officer", "Staff Nurse", "Auxiliary Nurse Midwife", "Pharmacist", "Health Worker", "Field Nurse"};
            for (int i = workers.size(); i < 6; i++) {
                HealthWorker w = new HealthWorker(); w.setFullName(workerNames[i]); w.setPhone("90000000" + (10 + i));
                w.setDesignation(designations[i]); w.setHealthCentre(centres.get(i % centres.size())); workers.add(workerRepo.save(w));
            }

            List<Resident> residents = residentRepo.findAll();
            String[] names = {"Priya Lakshmi", "Arun Kumar", "Meena Devi", "Ravi Selvam", "Lakshmi Narayan", "Kannan Murugan", "Selvi Raj", "Muthuvel Kumar", "Devi Shankar", "Ramesh Babu", "Nila Rani", "Suresh Anand", "Vani Priya", "Bala Murugan", "Kamala Devi"};
            Gender[] genders = Gender.values();
            for (int i = residents.size(); i < 15; i++) {
                Resident r = new Resident(); r.setFullName(names[i]); r.setGender(genders[i % genders.length]);
                r.setDateOfBirth(LocalDate.now().minusYears(18 + i * 3L)); r.setPhone("91000000" + (10 + i));
                r.setVillage(centreLocations[i % centreLocations.length]); r.setAddress("Main Road, " + r.getVillage());
                residents.add(residentRepo.save(r));
            }

            List<Appointment> appointments = appointmentRepo.findAll();
            for (int i = appointments.size(); i < 12; i++) {
                Appointment a = new Appointment(); a.setResident(residents.get(i % residents.size()));
                HealthWorker w = workers.get(i % workers.size()); a.setHealthWorker(w); a.setHealthCentre(w.getHealthCentre());
                a.setScheduledAt(LocalDateTime.now().plusDays(i + 1).withHour(9 + (i % 6)).withMinute(0));
                a.setStatus(i % 5 == 0 ? AppointmentStatus.COMPLETED : AppointmentStatus.SCHEDULED);
                a.setReason(new String[]{"Routine check-up", "Maternal health review", "Fever consultation", "Child wellness visit"}[i % 4]);
                appointmentRepo.save(a);
            }

            List<HealthCamp> camps = campRepo.findAll();
            for (int i = camps.size(); i < 4; i++) {
                HealthCamp c = new HealthCamp(); c.setName(new String[]{"Village Wellness Camp", "Maternal Health Camp", "Eye Screening Camp", "Immunization Camp"}[i]);
                c.setLocation(centreLocations[i % centreLocations.length]); c.setStartDate(LocalDate.now().plusDays(i + 2));
                c.setEndDate(c.getStartDate().plusDays(1)); c.setStatus(CampStatus.PLANNED); c.setHealthCentre(centres.get(i % centres.size()));
                camps.add(campRepo.save(c));
            }
            Set<String> pairs = new HashSet<>();
            for (CampBeneficiary b : beneficiaryRepo.findAll()) pairs.add(b.getCamp().getId() + ":" + b.getResident().getId());
            long beneficiaries = beneficiaryRepo.count();
            outer: for (HealthCamp camp : camps) for (Resident resident : residents) {
                if (beneficiaries >= 10) break outer;
                String key = camp.getId() + ":" + resident.getId(); if (!pairs.add(key)) continue;
                CampBeneficiary b = new CampBeneficiary(); b.setCamp(camp); b.setResident(resident);
                b.setScreeningResult("Demo screening completed; routine follow-up advised");
                b.setFollowUpStatus(beneficiaries % 3 == 0 ? FollowUpStatus.PENDING : FollowUpStatus.NOT_REQUIRED);
                beneficiaryRepo.save(b); beneficiaries++;
            }
        };
    }
}
