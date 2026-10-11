package com.ruralhealth.publichealth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruralhealth.publichealth.entity.Medicine;
import java.util.List;
import com.ruralhealth.publichealth.entity.enums.MedicineType;
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
 List<Medicine> findByHealthCentreId(Long healthCentreId);
}
