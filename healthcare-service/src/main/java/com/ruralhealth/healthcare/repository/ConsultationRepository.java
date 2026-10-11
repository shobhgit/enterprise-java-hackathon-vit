
package com.ruralhealth.healthcare.repository;

import com.ruralhealth.healthcare.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultationRepository
        extends JpaRepository<Consultation, Long> {

    Optional<Consultation> findByAppointment_Id(Long appointmentId);

    List<Consultation> findByAppointment_Resident_IdOrderByConsultedAtDesc(Long residentId);
}
