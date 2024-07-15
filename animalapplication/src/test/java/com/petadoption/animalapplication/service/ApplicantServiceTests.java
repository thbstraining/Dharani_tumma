package com.petadoption.animalapplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.mail.MessagingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.context.Context;

import com.petadoption.animalapplication.entity.Applicant;
import com.petadoption.animalapplication.repository.ApplicantRepository;

public class ApplicantServiceTests {

    @Mock
    private ApplicantRepository applicantRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ApplicantService applicantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Applicant> applicants = new ArrayList<>();
        applicants.add(new Applicant());
        when(applicantRepository.findAll()).thenReturn(applicants);

        List<Applicant> result = applicantService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindById_Found() {
        Applicant applicant = new Applicant();
        applicant.setId(1L);
        when(applicantRepository.findById(1L)).thenReturn(Optional.of(applicant));

        Optional<Applicant> result = applicantService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testFindById_NotFound() {
        when(applicantRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Applicant> result = applicantService.findById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        Applicant applicant = new Applicant();
        when(applicantRepository.save(any(Applicant.class))).thenReturn(applicant);

        Applicant result = applicantService.save(applicant);
        assertNotNull(result);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(applicantRepository).deleteById(1L);

        applicantService.deleteById(1L);
        verify(applicantRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateApplicationStatus_Accepted() throws MessagingException {
        Applicant applicant = new Applicant();
        applicant.setEmail("applicant@example.com");
        when(applicantRepository.findById(1L)).thenReturn(Optional.of(applicant));

        doNothing().when(emailService).sendApplicationStatusEmail(anyString(), anyString(), anyString(), any(Context.class));

        applicantService.updateApplicationStatus(1L, "Fluffy", true);

        verify(emailService, times(1)).sendApplicationStatusEmail(
            eq("applicant@example.com"),
            eq("Your Application has been Accepted!"),
            eq("application-accepted"),
            any(Context.class)
        );
    }

    @Test
    public void testUpdateApplicationStatus_Rejected() throws MessagingException {
        Applicant applicant = new Applicant();
        applicant.setEmail("applicant@example.com");
        when(applicantRepository.findById(1L)).thenReturn(Optional.of(applicant));

        doNothing().when(emailService).sendApplicationStatusEmail(anyString(), anyString(), anyString(), any(Context.class));

        applicantService.updateApplicationStatus(1L, "Fluffy", false);

        verify(emailService, times(1)).sendApplicationStatusEmail(
            eq("applicant@example.com"),
            eq("Your Application has been Rejected"),
            eq("application-rejected"),
            any(Context.class)
        );
    }

    @Test
    public void testUpdateApplicationStatus_AppNotFound() throws MessagingException {
        when(applicantRepository.findById(1L)).thenReturn(Optional.empty());

        applicantService.updateApplicationStatus(1L, "Fluffy", true);

        verify(emailService, never()).sendApplicationStatusEmail(anyString(), anyString(), anyString(), any(Context.class));
    }
}
