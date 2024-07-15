package com.petadoption.animalapplication.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import com.petadoption.animalapplication.entity.AdoptionApplication;
import com.petadoption.animalapplication.service.AdoptionApplicationService;
import com.petadoption.animalapplication.service.EmailService;
import com.petadoption.animalinformation.entity.Animal;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/adoption-applications")
public class AdoptionApplicationController {

 @Autowired
 private AdoptionApplicationService adoptionApplicationService;
 @Autowired
 private RestTemplate restTemplate;
 @Autowired
 private EmailService emailService;
 // Base URL of AnimalInformation MicroService registered in Eureka Server
 private final String ANIMAL_SERVICE_URL = "http://localhost:2222";
 
 @GetMapping
 public List<AdoptionApplication> getAllApplications() {
     return adoptionApplicationService.getAllApplications();
 }

 @GetMapping("/{id}")
 public AdoptionApplication getApplicationById(@PathVariable Long id) {
     return adoptionApplicationService.getApplicationById(id);
 }

 
 @PostMapping
 public AdoptionApplication createApplication(@Valid @RequestBody AdoptionApplication application) {
     // Fetch animal details from AnimalInformationService
	 String animalUrl = ANIMAL_SERVICE_URL + "/api/animals/" + application.getAnimalId();
     Animal animal = restTemplate.getForObject(animalUrl, Animal.class);

   
     return adoptionApplicationService.submitApplication(application);
 }

 @PutMapping("/{id}/status")
 public AdoptionApplication updateApplicationStatus(@PathVariable Long id, @RequestParam String status, @RequestParam String notes) {
     return adoptionApplicationService.updateApplicationStatus(id, status, notes);
 }

 @DeleteMapping("/{id}")
 public void deleteApplication(@PathVariable Long id) {
     adoptionApplicationService.deleteApplication(id);
 }
}
