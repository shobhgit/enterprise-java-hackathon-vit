package com.ruralhealth.publichealth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruralhealth.publichealth.entity.SanitationComplaint;
import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import java.util.List;
public interface SanitationComplaintRepository extends JpaRepository<SanitationComplaint, Long> {
 List<SanitationComplaint> findByStatus(WorkStatus status);
 List<SanitationComplaint> findAllByOrderByCreatedAtDesc();
 long countByStatusIn(List<WorkStatus> statuses);
}
