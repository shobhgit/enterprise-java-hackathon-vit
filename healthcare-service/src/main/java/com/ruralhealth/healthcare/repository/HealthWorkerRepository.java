
package com.ruralhealth.healthcare.repository;

import com.ruralhealth.healthcare.entity.HealthWorker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthWorkerRepository
        extends JpaRepository<HealthWorker, Long> {

    List<HealthWorker> findByHealthCentre_Id(Long healthCentreId);
    List<HealthWorker> findAll();
}
