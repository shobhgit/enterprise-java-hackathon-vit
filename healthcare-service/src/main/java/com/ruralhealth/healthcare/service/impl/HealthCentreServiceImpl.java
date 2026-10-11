
package com.ruralhealth.healthcare.service.impl;

import com.ruralhealth.healthcare.dto.HealthCentreForm;
import com.ruralhealth.healthcare.entity.HealthCentre;
import com.ruralhealth.healthcare.exception.ResourceNotFoundException;
import com.ruralhealth.healthcare.repository.HealthCentreRepository;
import com.ruralhealth.healthcare.service.HealthCentreService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HealthCentreServiceImpl implements HealthCentreService {

    private final HealthCentreRepository healthCentreRepository;

    public HealthCentreServiceImpl(HealthCentreRepository healthCentreRepository) {this.healthCentreRepository = healthCentreRepository;
    }

    @Override
    public List<HealthCentre> getAllCentres() {
        return healthCentreRepository.findAll();
    }

    @Override
    public HealthCentre getCentreById(Long id) {
        return healthCentreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Health centre not found with ID: " + id));
    }

    @Override
    @Transactional
    public HealthCentre createCentre(HealthCentreForm form) {
        HealthCentre centre = new HealthCentre();

        centre.setName(form.getName().trim());
        centre.setLocation(form.getLocation().trim());

        return healthCentreRepository.save(centre);
    }
}
