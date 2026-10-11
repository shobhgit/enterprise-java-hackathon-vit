
package com.ruralhealth.healthcare.service;

import com.ruralhealth.healthcare.dto.AppointmentForm;
import com.ruralhealth.healthcare.dto.ConsultationForm;
import com.ruralhealth.healthcare.entity.Appointment;
import com.ruralhealth.healthcare.entity.Consultation;
import com.ruralhealth.healthcare.entity.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByStatus(AppointmentStatus status);

    Appointment getAppointmentById(Long id);

    Consultation getConsultationForAppointment(Long appointmentId);

    void updateStatus(Long appointmentId, AppointmentStatus status);

    Appointment createAppointment(AppointmentForm form);

    Consultation createConsultation(
            Long appointmentId,
            ConsultationForm form
    );
}
