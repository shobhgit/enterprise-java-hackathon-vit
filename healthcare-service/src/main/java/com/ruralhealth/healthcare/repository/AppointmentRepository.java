
package com.ruralhealth.healthcare.repository;
import com.ruralhealth.healthcare.entity.Appointment;
import com.ruralhealth.healthcare.entity.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByResident_IdOrderByScheduledAtDesc(Long residentId);
}
