package com.ruralhealth.healthcare.repository;
import com.ruralhealth.healthcare.entity.CampBeneficiary;
import com.ruralhealth.healthcare.entity.enums.FollowUpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampBeneficiaryRepository
        extends JpaRepository<CampBeneficiary, Long> {

    List<CampBeneficiary> findByCamp_Id(Long campId);

    List<CampBeneficiary> findByFollowUpStatus(FollowUpStatus followUpStatus);
}
