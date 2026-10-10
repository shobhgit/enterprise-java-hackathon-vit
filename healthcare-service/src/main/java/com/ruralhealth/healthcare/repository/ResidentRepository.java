
package com.ruralhealth.healthcare.repository;

import com.ruralhealth.healthcare.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepository
        extends JpaRepository<Resident, Long> {
}
