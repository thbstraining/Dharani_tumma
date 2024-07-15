package com.petadoption.animalapplication.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendApplicationStatusEmail(String to, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        
        // Set email properties
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        
        // Generate email content using Thymeleaf
        String htmlContent = templateEngine.process(templateName, context);
        messageHelper.setText(htmlContent, true);

        emailSender.send(mimeMessage);
    }
}
