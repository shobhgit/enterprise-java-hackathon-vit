
package com.ruralhealth.healthcare.service.impl;

import com.ruralhealth.healthcare.dto.HealthCampForm;
import com.ruralhealth.healthcare.entity.CampBeneficiary;
import com.ruralhealth.healthcare.entity.HealthCamp;
import com.ruralhealth.healthcare.entity.HealthCentre;
import com.ruralhealth.healthcare.entity.Resident;
import com.ruralhealth.healthcare.entity.enums.CampStatus;
import com.ruralhealth.healthcare.entity.enums.FollowUpStatus;
import com.ruralhealth.healthcare.exception.ResourceNotFoundException;
import com.ruralhealth.healthcare.repository.CampBeneficiaryRepository;
import com.ruralhealth.healthcare.repository.HealthCampRepository;
import com.ruralhealth.healthcare.repository.HealthCentreRepository;
import com.ruralhealth.healthcare.repository.ResidentRepository;
import com.ruralhealth.healthcare.service.HealthCampService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HealthCampServiceImpl implements HealthCampService {

    private final HealthCampRepository healthCampRepository;
    private final CampBeneficiaryRepository beneficiaryRepository;
    private final ResidentRepository residentRepository;
    private final HealthCentreRepository healthCentreRepository;

    public HealthCampServiceImpl(
            HealthCampRepository healthCampRepository,
            CampBeneficiaryRepository beneficiaryRepository,
            ResidentRepository residentRepository,
            HealthCentreRepository healthCentreRepository) {

        this.healthCampRepository = healthCampRepository;
        this.beneficiaryRepository = beneficiaryRepository;
        this.residentRepository = residentRepository;
        this.healthCentreRepository = healthCentreRepository;
    }

    @Override
    public List<HealthCamp> getAllCamps() {
        return healthCampRepository.findAll();
    }

    @Override
    public HealthCamp getCampById(Long id) {
        return healthCampRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Health camp not found with ID: " + id));
    }

    @Override
    @Transactional
    public HealthCamp createCamp(HealthCampForm form) {

        if (form.getEndDate().isBefore(form.getStartDate())) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date.");
        }

        HealthCamp camp = new HealthCamp();
        camp.setName(form.getName().trim());
        camp.setLocation(form.getLocation().trim());
        camp.setStartDate(form.getStartDate());
        camp.setEndDate(form.getEndDate());
        camp.setStatus(CampStatus.PLANNED);

        if (form.getHealthCentreId() != null) {
            HealthCentre centre = healthCentreRepository
                    .findById(form.getHealthCentreId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Health centre not found with ID: "
                                    + form.getHealthCentreId()));

            camp.setHealthCentre(centre);
        }

        return healthCampRepository.save(camp);
    }

    @Override
    public List<CampBeneficiary> getBeneficiaries(Long campId) {
        getCampById(campId);
        return beneficiaryRepository.findByCamp_Id(campId);
    }

    @Override
    @Transactional
    public CampBeneficiary addBeneficiary(
            Long campId,
            Long residentId,
            String screeningResult) {

        HealthCamp camp = getCampById(campId);

        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resident not found with ID: " + residentId));

        boolean alreadyRegistered = beneficiaryRepository
                .findByCamp_Id(campId)
                .stream()
                .anyMatch(beneficiary ->
                        beneficiary.getResident().getId().equals(residentId));

        if (alreadyRegistered) {
            throw new IllegalArgumentException(
                    "This resident is already registered for the camp.");
        }

        CampBeneficiary beneficiary = new CampBeneficiary();
        beneficiary.setCamp(camp);
        beneficiary.setResident(resident);
        beneficiary.setScreeningResult(screeningResult);
        beneficiary.setFollowUpStatus(FollowUpStatus.NOT_REQUIRED);

        return beneficiaryRepository.save(beneficiary);
    }

    @Override
    @Transactional
    public CampBeneficiary updateFollowUpStatus(
            Long beneficiaryId,
            FollowUpStatus status) {

        CampBeneficiary beneficiary = beneficiaryRepository
                .findById(beneficiaryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Camp beneficiary not found with ID: " + beneficiaryId));

        beneficiary.setFollowUpStatus(status);

        return beneficiaryRepository.save(beneficiary);
    }

    @Override
    @Transactional
    public void updateCampStatus(Long campId, CampStatus status) {
        HealthCamp camp = getCampById(campId);
        camp.setStatus(status);
        healthCampRepository.save(camp);
    }
}
