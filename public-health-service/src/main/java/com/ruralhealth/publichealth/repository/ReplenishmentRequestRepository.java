package com.ruralhealth.publichealth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruralhealth.publichealth.entity.ReplenishmentRequest;
import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import java.util.List;
public interface ReplenishmentRequestRepository extends JpaRepository<ReplenishmentRequest, Long> {
 List<ReplenishmentRequest> findByStatus(WorkStatus status);
 List<ReplenishmentRequest> findByHealthCentreId(Long healthCentreId);
 List<ReplenishmentRequest> findByStatusAndHealthCentreId(WorkStatus status, Long healthCentreId);
 long countByStatusIn(List<WorkStatus> statuses);
}
