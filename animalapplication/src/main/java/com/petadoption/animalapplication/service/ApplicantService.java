package com.petadoption.animalapplication.service;


import com.petadoption.animalapplication.entity.Applicant;
import com.petadoption.animalapplication.repository.ApplicantRepository;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private EmailService emailService;

    public List<Applicant> findAll() {
        return applicantRepository.findAll();
    }

    public Optional<Applicant> findById(Long id) {
        return applicantRepository.findById(id);
    }

    public Applicant save(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    public void deleteById(Long id) {
        applicantRepository.deleteById(id);
    }
    public void updateApplicationStatus(Long applicantId, String animalName, boolean isAccepted) throws MessagingException {
        Optional<Applicant> applicantOpt = applicantRepository.findById(applicantId);
        if (applicantOpt.isPresent()) {
            Applicant applicant = applicantOpt.get();
            Context context = new Context();
            context.setVariable("applicant", applicant);
            context.setVariable("animalName", animalName);

            String subject;
            String templateName;

            if (isAccepted) {
                subject = "Your Application has been Accepted!";
                templateName = "application-accepted";
            } else {
                subject = "Your Application has been Rejected";
                templateName = "application-rejected";
            }

            try {
				emailService.sendApplicationStatusEmail(applicant.getEmail(), subject, templateName, context);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
        } else {
            System.err.println("Applicant not found with ID: " + applicantId);
        }
    }
}
