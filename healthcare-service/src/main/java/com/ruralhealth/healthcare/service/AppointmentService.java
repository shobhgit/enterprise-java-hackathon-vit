
package com.ruralhealth.healthcare.service;

import com.ruralhealth.healthcare.dto.AppointmentForm;
import com.ruralhealth.healthcare.dto.ConsultationForm;
import com.ruralhealth.healthcare.entity.Appointment;
import com.ruralhealth.healthcare.entity.Consultation;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    Appointment getAppointmentById(Long id);

    Appointment createAppointment(AppointmentForm form);

    Consultation createConsultation(
            Long appointmentId,
            ConsultationForm form
    );
}
