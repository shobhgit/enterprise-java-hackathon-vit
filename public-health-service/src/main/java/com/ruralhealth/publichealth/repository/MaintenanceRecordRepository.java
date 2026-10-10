package com.ruralhealth.publichealth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruralhealth.publichealth.entity.MaintenanceRecord;
import java.util.List;
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {
 List<MaintenanceRecord> findByFacilityIdOrderByPerformedOnDesc(Long facilityId);
}
