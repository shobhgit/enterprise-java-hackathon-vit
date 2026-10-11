package com.ruralhealth.healthcare.repository;
import com.ruralhealth.healthcare.entity.CampBeneficiary;
import com.ruralhealth.healthcare.entity.enums.FollowUpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;

public interface CampBeneficiaryRepository
        extends JpaRepository<CampBeneficiary, Long> {

    List<CampBeneficiary> findByCamp_Id(Long campId);

    @Override
    @EntityGraph(attributePaths = {"camp", "resident"})
    List<CampBeneficiary> findAll();

    List<CampBeneficiary> findByFollowUpStatus(FollowUpStatus followUpStatus);
}
