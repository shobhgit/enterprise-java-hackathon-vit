
package com.ruralhealth.healthcare.repository;

import com.ruralhealth.healthcare.entity.HealthCentre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCentreRepository
        extends JpaRepository<HealthCentre, Long> {
}
