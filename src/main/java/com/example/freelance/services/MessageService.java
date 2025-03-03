package com.example.freelance.services;

import com.example.freelance.entites.ChatRoom;
import com.example.freelance.entites.Message;
import com.example.freelance.entites.User;
import com.example.freelance.repos.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.example.freelance.repos.MessageRepository;
import com.example.freelance.repos.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;  // WebSocket Messaging

    // Envoyer un message
    public Message sendMessage(Long senderId, Long receiverId, String content) {
        // Récupérer les utilisateurs
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        // Vérifier si une chat room existe déjà entre les utilisateurs
        ChatRoom chatRoom = chatRoomRepository.findByUser1AndUser2(sender, receiver)
                .orElseGet(() -> chatRoomRepository.findByUser1AndUser2(receiver, sender)
                        .orElseGet(() -> {
                            // Si aucune chat room n'existe, on en crée une nouvelle
                            ChatRoom newChatRoom = new ChatRoom();
                            newChatRoom.setUser1(sender);
                            newChatRoom.setUser2(receiver);
                            newChatRoom.setChatId(generateChatId(senderId, receiverId));
                            return chatRoomRepository.save(newChatRoom);
                        }));

        // Créer et sauvegarder le message
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSeen(false);
        message.setChatRoom(chatRoom);

        Message savedMessage = messageRepository.save(message);

        // 🔴 WebSocket : Envoyer le message en temps réel
        messagingTemplate.convertAndSend("/topic/messages/" + receiverId, savedMessage);

        return savedMessage;
    }

    // Générer l'ID de chat pour la chat room
    private String generateChatId(Long user1Id, Long user2Id) {
        return (user1Id < user2Id) ? user1Id + "-" + user2Id : user2Id + "-" + user1Id;
    }

    // Récupérer tous les messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Récupérer les messages entre deux utilisateurs
    public List<Message> getMessages(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId).orElseThrow();
        User receiver = userRepository.findById(receiverId).orElseThrow();
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }

    // Marquer un message comme vu
    public Message markMessageAsSeen(Long messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setSeen(true);
            Message updatedMessage = messageRepository.save(message);

            // 🔴 WebSocket : Notifier le sender que le message est vu
            messagingTemplate.convertAndSend("/topic/messages/seen/" + message.getSender().getId(), updatedMessage);

            return updatedMessage;
        } else {
            throw new RuntimeException("Message introuvable avec l'ID : " + messageId);
        }
    }

    // Mettre à jour un message
    public Message updateMessage(Long messageId, String newContent) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setContent(newContent);
            return messageRepository.save(message);
        } else {
            throw new RuntimeException("Message introuvable avec l'ID : " + messageId);
        }
    }

    // Supprimer un message
    public void deleteMessage(Long messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
        } else {
            throw new RuntimeException("Message introuvable avec l'ID : " + messageId);
        }
    }
}
