package com.ruralhealth.publichealth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruralhealth.publichealth.entity.MedicineIssue;
import java.util.List;
public interface MedicineIssueRepository extends JpaRepository<MedicineIssue, Long> {
 List<MedicineIssue> findByMedicineIdOrderByIssuedOnDesc(Long medicineId);
}
