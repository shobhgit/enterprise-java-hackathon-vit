
package com.ruralhealth.healthcare.service;

import com.ruralhealth.healthcare.dto.ResidentForm;
import com.ruralhealth.healthcare.entity.Appointment;
import com.ruralhealth.healthcare.entity.Consultation;
import com.ruralhealth.healthcare.entity.Resident;

import java.util.List;

public interface ResidentService {

    List<Resident> getAllResidents();

    Resident getResidentById(Long id);

    Resident createResident(ResidentForm form);

    List<Appointment> getAppointmentsForResident(Long residentId);

    List<Consultation> getConsultationsForResident(Long residentId);
}
