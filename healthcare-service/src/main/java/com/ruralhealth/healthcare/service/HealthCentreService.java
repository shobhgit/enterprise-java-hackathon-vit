package com.ruralhealth.healthcare.service;

import com.ruralhealth.healthcare.dto.HealthCentreForm;
import com.ruralhealth.healthcare.entity.HealthCentre;

import java.util.List;

public interface HealthCentreService {

    List<HealthCentre> getAllCentres();

    HealthCentre getCentreById(Long id);

    HealthCentre createCentre(HealthCentreForm form);
}