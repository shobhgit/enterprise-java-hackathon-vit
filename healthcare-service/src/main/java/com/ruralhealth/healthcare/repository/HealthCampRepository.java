
package com.ruralhealth.healthcare.repository;

import com.ruralhealth.healthcare.entity.HealthCamp;
import com.ruralhealth.healthcare.entity.enums.CampStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthCampRepository
        extends JpaRepository<HealthCamp, Long> {

    List<HealthCamp> findByStatusOrderByStartDateAsc(CampStatus status);
}
