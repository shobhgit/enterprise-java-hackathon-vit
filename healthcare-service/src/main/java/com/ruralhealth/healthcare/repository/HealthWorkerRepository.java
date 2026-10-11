
package com.ruralhealth.healthcare.repository;

import com.ruralhealth.healthcare.entity.HealthWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface HealthWorkerRepository
        extends JpaRepository<HealthWorker, Long> {

    List<HealthWorker> findByHealthCentre_Id(Long healthCentreId);
    @Override
    @EntityGraph(attributePaths = "healthCentre")
    List<HealthWorker> findAll();
}
