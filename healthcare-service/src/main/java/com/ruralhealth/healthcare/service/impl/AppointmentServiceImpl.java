
package com.ruralhealth.healthcare.service.impl;

import com.ruralhealth.healthcare.dto.AppointmentForm;
import com.ruralhealth.healthcare.dto.ConsultationForm;
import com.ruralhealth.healthcare.entity.Appointment;
import com.ruralhealth.healthcare.entity.Consultation;
import com.ruralhealth.healthcare.entity.HealthCentre;
import com.ruralhealth.healthcare.entity.HealthWorker;
import com.ruralhealth.healthcare.entity.Resident;
import com.ruralhealth.healthcare.entity.enums.AppointmentStatus;
import com.ruralhealth.healthcare.exception.ResourceNotFoundException;
import com.ruralhealth.healthcare.repository.AppointmentRepository;
import com.ruralhealth.healthcare.repository.ConsultationRepository;
import com.ruralhealth.healthcare.repository.HealthCentreRepository;
import com.ruralhealth.healthcare.repository.HealthWorkerRepository;
import com.ruralhealth.healthcare.repository.ResidentRepository;
import com.ruralhealth.healthcare.service.AppointmentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ConsultationRepository consultationRepository;
    private final ResidentRepository residentRepository;
    private final HealthCentreRepository healthCentreRepository;
    private final HealthWorkerRepository healthWorkerRepository;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            ConsultationRepository consultationRepository,
            ResidentRepository residentRepository,
            HealthCentreRepository healthCentreRepository,
            HealthWorkerRepository healthWorkerRepository) {

        this.appointmentRepository = appointmentRepository;
        this.consultationRepository = consultationRepository;
        this.residentRepository = residentRepository;
        this.healthCentreRepository = healthCentreRepository;
        this.healthWorkerRepository = healthWorkerRepository;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with ID: " + id));
    }

    @Override
    public Consultation getConsultationForAppointment(Long appointmentId) {
        return consultationRepository.findByAppointment_Id(appointmentId).orElse(null);
    }

    @Override
    @Transactional
    public void updateStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment.getStatus() != AppointmentStatus.SCHEDULED || status != AppointmentStatus.CANCELLED) {
            throw new IllegalArgumentException("Only scheduled appointments can be cancelled.");
        }
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public Appointment createAppointment(AppointmentForm form) {

        Resident resident = residentRepository.findById(form.getResidentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resident not found with ID: " + form.getResidentId()));

        HealthCentre centre =
                healthCentreRepository.findById(form.getHealthCentreId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Health centre not found with ID: "
                                        + form.getHealthCentreId()));

        HealthWorker worker =
                healthWorkerRepository.findById(form.getHealthWorkerId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Health worker not found with ID: "
                                        + form.getHealthWorkerId()));

        if (!worker.getHealthCentre().getId().equals(centre.getId())) {
            throw new IllegalArgumentException(
                    "The selected health worker does not belong to this health centre.");
        }

        Appointment appointment = new Appointment();
        appointment.setResident(resident);
        appointment.setHealthCentre(centre);
        appointment.setHealthWorker(worker);
        appointment.setScheduledAt(form.getScheduledAt());
        appointment.setReason(form.getReason());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public Consultation createConsultation(
            Long appointmentId,
            ConsultationForm form) {

        Appointment appointment = getAppointmentById(appointmentId);

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalArgumentException(
                    "A consultation can only be recorded for a scheduled appointment.");
        }

        if (consultationRepository.findByAppointment_Id(appointmentId).isPresent()) {
            throw new IllegalArgumentException(
                    "A consultation already exists for this appointment.");
        }

        Consultation consultation = new Consultation();
        consultation.setAppointment(appointment);
        consultation.setDiagnosis(form.getDiagnosis());
        consultation.setPrescription(form.getPrescription());
        consultation.setNotes(form.getNotes());
        consultation.setConsultedAt(LocalDateTime.now());

        Consultation savedConsultation =
                consultationRepository.save(consultation);

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        return savedConsultation;
    }
}
