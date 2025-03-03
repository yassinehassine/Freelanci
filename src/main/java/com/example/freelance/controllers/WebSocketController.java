package com.example.freelance.controllers;

import com.example.freelance.entites.Message;
import com.example.freelance.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat")
    public void sendMessage(Message message) {
        try {
            // Send message to the database
            Message savedMessage = messageService.sendMessage(
                    message.getSender().getId(),
                    message.getReceiver().getId(),
                    message.getContent()
            );

            // Send the saved message to the receiver's WebSocket session in real-time
            messagingTemplate.convertAndSend("/queue/messages/" + message.getReceiver().getId(), savedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

