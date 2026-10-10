
package com.ruralhealth.healthcare.service;

import com.ruralhealth.healthcare.dto.HealthCampForm;
import com.ruralhealth.healthcare.entity.CampBeneficiary;
import com.ruralhealth.healthcare.entity.HealthCamp;
import com.ruralhealth.healthcare.entity.enums.CampStatus;
import com.ruralhealth.healthcare.entity.enums.FollowUpStatus;

import java.util.List;

public interface HealthCampService {

    List<HealthCamp> getAllCamps();

    HealthCamp getCampById(Long id);

    HealthCamp createCamp(HealthCampForm form);

    List<CampBeneficiary> getBeneficiaries(Long campId);

    CampBeneficiary addBeneficiary(
            Long campId,
            Long residentId,
            String screeningResult
    );

    CampBeneficiary updateFollowUpStatus(
            Long beneficiaryId,
            FollowUpStatus status
    );

    void updateCampStatus(Long campId, CampStatus status);
}
