package com.petadoption.animalapplication.service;

import com.petadoption.animalapplication.dao.AdoptionApplicationDAO;
import com.petadoption.animalapplication.entity.AdoptionApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdoptionApplicationService {

    @Autowired
    private AdoptionApplicationDAO adoptionApplicationDAO;

    public List<AdoptionApplication> getAllApplications() {
        return adoptionApplicationDAO.getAllApplications();
    }

    public AdoptionApplication getApplicationById(Long id) {
        return adoptionApplicationDAO.getApplicationById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    public AdoptionApplication submitApplication(AdoptionApplication application) {
        application.setAdoptionStatus("pending");
        application.setApplicationDate(LocalDateTime.now());
        adoptionApplicationDAO.insertApplication(application);
        return application;
    }

    public void deleteApplication(Long id) {
        adoptionApplicationDAO.deleteApplication(id);
    }

    public AdoptionApplication updateApplicationStatus(Long id, String status, String notes) {
        int rowsAffected = adoptionApplicationDAO.updateApplication(id, status, notes);
        if (rowsAffected == 0) {
            throw new RuntimeException("Application not found");
        }
        return getApplicationById(id);
    }
}
