package com.example.freelance.services;
import com.example.freelance.entites.ChatRoom;
import com.example.freelance.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.freelance.repos.ChatRoomRepository;
import com.example.freelance.repos.UserRepository;

import java.util.List;

@Service

public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    // Créer une conversation entre deux utilisateurs
    public ChatRoom createChatRoom(Long user1Id, Long user2Id) {
        // Vérifie que les deux utilisateurs existent
        User user1 = userRepository.findById(user1Id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + user1Id));
        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + user2Id));

        // Crée une nouvelle conversation (ChatRoom)
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUser1(user1);
        chatRoom.setUser2(user2);

        return chatRoomRepository.save(chatRoom);
    }

    // Récupérer les conversations d'un utilisateur
    public List<ChatRoom> getUserChatRooms(Long userId) {
        // Récupère les conversations où l'utilisateur est impliqué
        return chatRoomRepository.findByUser1IdOrUser2Id(userId, userId);
    }
}
