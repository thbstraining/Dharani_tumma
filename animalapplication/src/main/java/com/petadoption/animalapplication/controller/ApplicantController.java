package com.petadoption.animalapplication.controller;


import com.petadoption.animalapplication.entity.Applicant;
import com.petadoption.animalapplication.service.ApplicantService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applicants")
@Validated
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @GetMapping
    public List<Applicant> getAllApplicants() {
        return applicantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable Long id) {
        Optional<Applicant> applicant = applicantService.findById(id);
        return applicant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Applicant createApplicant(@Valid @RequestBody Applicant applicant) {
        return applicantService.save(applicant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable Long id, @RequestBody Applicant applicant) {
        if (applicantService.findById(id).isPresent()) {
            applicant.setId(id);
            return ResponseEntity.ok(applicantService.save(applicant));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable Long id) {
        if (applicantService.findById(id).isPresent()) {
            applicantService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
