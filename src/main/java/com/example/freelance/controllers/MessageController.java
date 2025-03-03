package com.example.freelance.controllers;

import com.example.freelance.entites.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.freelance.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Envoyer un message
    @PostMapping
    public ResponseEntity<String> createMessage(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestBody String content) {
        messageService.sendMessage(senderId, receiverId, content); // Utiliser sendMessage
        return ResponseEntity.status(HttpStatus.CREATED).body("Message created");
    }

    // Récupérer les messages entre deux utilisateurs
    @GetMapping
    public List<Message> getMessages(@RequestParam Long senderId, @RequestParam Long receiverId) {
        return messageService.getMessages(senderId, receiverId);
    }

    @GetMapping("/all")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // Marquer un message comme vu
    @PutMapping("/mark-seen/{messageId}")
    public Message markMessageAsSeen(@PathVariable Long messageId) {
        return messageService.markMessageAsSeen(messageId);
    }

    // Mettre à jour un message
    @PutMapping("/{id}")
    public Message updateMessage(@PathVariable Long id, @RequestParam String newContent) {
        return messageService.updateMessage(id, newContent);
    }

    // Supprimer un message
    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return "Message supprimé avec succès.";
    }
}
