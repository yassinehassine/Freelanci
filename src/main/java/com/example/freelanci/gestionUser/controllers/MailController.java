package com.example.freelanci.gestionUser.controllers;

import com.example.freelanci.gestionUser.services.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String message) {
        try {
            mailService.sendEmail(to, subject, message);
            return "Email envoyé avec succès à " + to;
        } catch (MessagingException e) {
            return "Erreur lors de l'envoi de l'email : " + e.getMessage();
        }
    }
}
