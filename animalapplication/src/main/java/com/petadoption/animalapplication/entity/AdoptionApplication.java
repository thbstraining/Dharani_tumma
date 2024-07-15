package com.petadoption.animalapplication.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class AdoptionApplication {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Animal ID is mandatory")
    @Min(value = 1, message = "Animal ID must be greater than 0")
    private Long animalId;

    @NotBlank(message = "Applicant ID is mandatory")
    @Min(value = 1, message = "Applicant ID must be greater than 0")
    private Long applicantId;

    @NotBlank(message = "Application date is mandatory")
    @FutureOrPresent(message = "Application date must be in the present or future")
    private LocalDateTime applicationDate;

    @NotBlank(message = "Adoption status is mandatory")
    @Size(max = 50, message = "Adoption status must be either Availble, Pending or Adopted")
    private String adoptionStatus;

    @Size(max = 255, message = "Notes must be less than 255 characters")
    private String notes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAnimalId() {
		return animalId;
	}
	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}
	public Long getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}
	public LocalDateTime getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(LocalDateTime applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getAdoptionStatus() {
		return adoptionStatus;
	}
	public void setAdoptionStatus(String adoptionStatus) {
		this.adoptionStatus = adoptionStatus;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
