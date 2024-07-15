package com.petadoption.animalapplication.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.petadoption.animalapplication.entity.AdoptionApplication;
import com.petadoption.animalapplication.service.AdoptionApplicationService;
import com.petadoption.animalapplication.service.EmailService;
import com.petadoption.animalinformation.entity.Animal;

@SpringBootTest
public class AdoptionApplicationControllerTests {

    @Mock
    private AdoptionApplicationService adoptionApplicationService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AdoptionApplicationController adoptionApplicationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllApplications() {
        List<AdoptionApplication> applications = new ArrayList<>();
        applications.add(new AdoptionApplication());
        when(adoptionApplicationService.getAllApplications()).thenReturn(applications);

        List<AdoptionApplication> result = adoptionApplicationController.getAllApplications();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetApplicationById() {
        AdoptionApplication application = new AdoptionApplication();
        application.setId(1L);
        when(adoptionApplicationService.getApplicationById(1L)).thenReturn(application);

        AdoptionApplication result = adoptionApplicationController.getApplicationById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testCreateApplication() {
        AdoptionApplication application = new AdoptionApplication();
        application.setAnimalId(1L);
        application.setApplicantId(1L);
        application.setApplicationDate(LocalDateTime.now());
        application.setAdoptionStatus("Pending");
        application.setNotes("Some notes");

        Animal animal = new Animal(); 
        when(restTemplate.getForObject("http://localhost:2222/api/animals/1", Animal.class)).thenReturn(animal);
        when(adoptionApplicationService.submitApplication(any(AdoptionApplication.class))).thenReturn(application);

        AdoptionApplication result = adoptionApplicationController.createApplication(application);
        assertNotNull(result);
        assertEquals("Pending", result.getAdoptionStatus());
    }

    @Test
    public void testUpdateApplicationStatus() {
        AdoptionApplication application = new AdoptionApplication();
        application.setId(1L);
        application.setAdoptionStatus("Approved");
        when(adoptionApplicationService.updateApplicationStatus(1L, "Approved", "Approved by shelter")).thenReturn(application);

        AdoptionApplication result = adoptionApplicationController.updateApplicationStatus(1L, "Approved", "Approved by shelter");
        assertNotNull(result);
        assertEquals("Approved", result.getAdoptionStatus());
    }

    @Test
    public void testDeleteApplication() {
        doNothing().when(adoptionApplicationService).deleteApplication(1L);

        adoptionApplicationController.deleteApplication(1L);
        verify(adoptionApplicationService, times(1)).deleteApplication(1L);
    }
}
