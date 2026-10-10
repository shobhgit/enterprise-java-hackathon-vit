
package com.ruralhealth.healthcare.service.impl;

import com.ruralhealth.healthcare.dto.ResidentForm;
import com.ruralhealth.healthcare.entity.Appointment;
import com.ruralhealth.healthcare.entity.Consultation;
import com.ruralhealth.healthcare.entity.Resident;
import com.ruralhealth.healthcare.exception.ResourceNotFoundException;
import com.ruralhealth.healthcare.repository.AppointmentRepository;
import com.ruralhealth.healthcare.repository.ConsultationRepository;
import com.ruralhealth.healthcare.repository.ResidentRepository;
import com.ruralhealth.healthcare.service.ResidentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;
    private final AppointmentRepository appointmentRepository;
    private final ConsultationRepository consultationRepository;

    public ResidentServiceImpl(
            ResidentRepository residentRepository,
            AppointmentRepository appointmentRepository,
            ConsultationRepository consultationRepository) {

        this.residentRepository = residentRepository;
        this.appointmentRepository = appointmentRepository;
        this.consultationRepository = consultationRepository;
    }

    @Override
    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    @Override
    public Resident getResidentById(Long id) {
        return residentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resident not found with ID: " + id
                        )
                );
    }

    @Override
    @Transactional
    public Resident createResident(ResidentForm form) {

        Resident resident = new Resident();

        resident.setFullName(form.getFullName().trim());
        resident.setDateOfBirth(form.getDateOfBirth());
        resident.setGender(form.getGender());
        resident.setPhone(form.getPhone());
        resident.setVillage(form.getVillage());
        resident.setAddress(form.getAddress());

        return residentRepository.save(resident);
    }

    @Override
    public List<Appointment> getAppointmentsForResident(Long residentId) {
        return appointmentRepository
                .findByResident_IdOrderByScheduledAtDesc(residentId);
    }

    @Override
    public List<Consultation> getConsultationsForResident(Long residentId) {
        return consultationRepository
                .findByAppointment_Resident_IdOrderByConsultedAtDesc(
                        residentId
                );
    }
}
