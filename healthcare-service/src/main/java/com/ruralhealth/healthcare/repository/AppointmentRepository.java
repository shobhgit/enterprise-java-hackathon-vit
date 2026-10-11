
package com.ruralhealth.healthcare.repository;
import com.ruralhealth.healthcare.entity.Appointment;
import com.ruralhealth.healthcare.entity.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    @Override
    @EntityGraph(attributePaths = {"resident", "healthCentre", "healthWorker"})
    List<Appointment> findAll();

    @Override
    @EntityGraph(attributePaths = {"resident", "healthCentre", "healthWorker"})
    Optional<Appointment> findById(Long id);

    @EntityGraph(attributePaths = {"resident", "healthCentre", "healthWorker"})
    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByResident_IdOrderByScheduledAtDesc(Long residentId);
}
