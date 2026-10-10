package com.ruralhealth.publichealth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruralhealth.publichealth.entity.WaterRecord;
import com.ruralhealth.publichealth.entity.enums.WaterActionStatus;
import java.util.List;
public interface WaterRecordRepository extends JpaRepository<WaterRecord, Long> {
 List<WaterRecord> findByActionStatus(WaterActionStatus actionStatus);
 List<WaterRecord> findAllByOrderByCreatedAtDesc();
 long countByActionStatusIn(List<WaterActionStatus> statuses);
}
