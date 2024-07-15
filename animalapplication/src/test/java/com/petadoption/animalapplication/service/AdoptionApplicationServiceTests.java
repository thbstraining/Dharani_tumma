package com.petadoption.animalapplication.service;

import com.petadoption.animalapplication.dao.AdoptionApplicationDAO;
import com.petadoption.animalapplication.entity.AdoptionApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AdoptionApplicationServiceTests {

    @Mock
    private AdoptionApplicationDAO adoptionApplicationDAO;

    @InjectMocks
    private AdoptionApplicationService adoptionApplicationService;

    private AdoptionApplication application;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        application = new AdoptionApplication();
        application.setId(1L);
        application.setAnimalId(1L);
        application.setApplicantId(1L);
        application.setApplicationDate(LocalDateTime.now());
        application.setAdoptionStatus("pending");
        application.setNotes("Initial notes");
    }

    @Test
    public void testGetAllApplications() {
        when(adoptionApplicationDAO.getAllApplications()).thenReturn(List.of(application));

        List<AdoptionApplication> applications = adoptionApplicationService.getAllApplications();

        assertNotNull(applications);
        assertEquals(1, applications.size());
        assertEquals(application.getId(), applications.get(0).getId());
        verify(adoptionApplicationDAO, times(1)).getAllApplications();
    }

    @Test
    public void testGetApplicationById_Success() {
        when(adoptionApplicationDAO.getApplicationById(anyLong())).thenReturn(Optional.of(application));

        AdoptionApplication result = adoptionApplicationService.getApplicationById(1L);

        assertNotNull(result);
        assertEquals(application.getId(), result.getId());
        verify(adoptionApplicationDAO, times(1)).getApplicationById(anyLong());
    }

    @Test
    public void testGetApplicationById_NotFound() {
        when(adoptionApplicationDAO.getApplicationById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            adoptionApplicationService.getApplicationById(1L);
        });
        assertEquals("Application not found", thrown.getMessage());
        verify(adoptionApplicationDAO, times(1)).getApplicationById(anyLong());
    }

    @Test
    public void testSubmitApplication() {
        when(adoptionApplicationDAO.insertApplication(any(AdoptionApplication.class))).thenReturn(1);

        AdoptionApplication result = adoptionApplicationService.submitApplication(application);

        assertNotNull(result);
        assertEquals("pending", result.getAdoptionStatus());
        assertNotNull(result.getApplicationDate());
        verify(adoptionApplicationDAO, times(1)).insertApplication(any(AdoptionApplication.class));
    }

    @Test
    public void testDeleteApplication() {
        adoptionApplicationService.deleteApplication(1L);

        verify(adoptionApplicationDAO, times(1)).deleteApplication(anyLong());
    }

    @Test
    public void testUpdateApplicationStatus_Success() {
        when(adoptionApplicationDAO.updateApplication(anyLong(), anyString(), anyString())).thenReturn(1);
        when(adoptionApplicationDAO.getApplicationById(anyLong())).thenReturn(Optional.of(application));

        AdoptionApplication updatedApplication = adoptionApplicationService.updateApplicationStatus(1L, "APPROVED", "Updated notes");

        assertNotNull(updatedApplication);
        assertEquals("APPROVED", updatedApplication.getAdoptionStatus());
        assertEquals("Updated notes", updatedApplication.getNotes());
        verify(adoptionApplicationDAO, times(1)).updateApplication(anyLong(), anyString(), anyString());
        verify(adoptionApplicationDAO, times(1)).getApplicationById(anyLong());
    }

    @Test
    public void testUpdateApplicationStatus_NotFound() {
        when(adoptionApplicationDAO.updateApplication(anyLong(), anyString(), anyString())).thenReturn(0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            adoptionApplicationService.updateApplicationStatus(1L, "approved", "Updated notes");
        });
        assertEquals("Application not found", thrown.getMessage());
        verify(adoptionApplicationDAO, times(1)).updateApplication(anyLong(), anyString(), anyString());
    }
}
