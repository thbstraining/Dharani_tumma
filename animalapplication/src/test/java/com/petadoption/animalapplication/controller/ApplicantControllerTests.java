package com.petadoption.animalapplication.controller;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.petadoption.animalapplication.entity.Applicant;
import com.petadoption.animalapplication.service.ApplicantService;

public class ApplicantControllerTests {

    @Mock
    private ApplicantService applicantService;

    @InjectMocks
    private ApplicantController applicantController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllApplicants() {
        List<Applicant> applicants = new ArrayList<>();
        applicants.add(new Applicant());
        when(applicantService.findAll()).thenReturn(applicants);

        List<Applicant> result = applicantController.getAllApplicants();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetApplicantById_Found() {
        Applicant applicant = new Applicant();
        applicant.setId(1L);
        when(applicantService.findById(1L)).thenReturn(Optional.of(applicant));

        ResponseEntity<Applicant> result = applicantController.getApplicantById(1L);
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(applicant, result.getBody());
    }

    @Test
    public void testGetApplicantById_NotFound() {
        when(applicantService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Applicant> result = applicantController.getApplicantById(1L);
        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue());
    }

    @Test
    public void testCreateApplicant() {
        Applicant applicant = new Applicant();
        when(applicantService.save(any(Applicant.class))).thenReturn(applicant);

        Applicant result = applicantController.createApplicant(applicant);
        assertNotNull(result);
        assertEquals(applicant, result);
    }

    @Test
    public void testUpdateApplicant_Found() {
        Applicant applicant = new Applicant();
        applicant.setId(1L);
        when(applicantService.findById(1L)).thenReturn(Optional.of(applicant));
        when(applicantService.save(any(Applicant.class))).thenReturn(applicant);

        ResponseEntity<Applicant> result = applicantController.updateApplicant(1L, applicant);
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(applicant, result.getBody());
    }

    @Test
    public void testUpdateApplicant_NotFound() {
        Applicant applicant = new Applicant();
        when(applicantService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Applicant> result = applicantController.updateApplicant(1L, applicant);
        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue());
    }

    @Test
    public void testDeleteApplicant_Found() {
        when(applicantService.findById(1L)).thenReturn(Optional.of(new Applicant()));

        ResponseEntity<Void> result = applicantController.deleteApplicant(1L);
        assertNotNull(result);
        assertEquals(204, result.getStatusCodeValue());
        verify(applicantService, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteApplicant_NotFound() {
        when(applicantService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> result = applicantController.deleteApplicant(1L);
        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue());
        verify(applicantService, times(0)).deleteById(1L);
    }
}
