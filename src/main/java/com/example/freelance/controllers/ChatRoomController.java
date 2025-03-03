package com.example.freelance.controllers;
import com.example.freelance.entites.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.freelance.services.ChatRoomService;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")

public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/{id}")
    public ChatRoom createChatRoom(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return chatRoomService.createChatRoom(user1Id, user2Id);
    }

    @GetMapping("/{id}")
    public List<ChatRoom> getUserChatRooms(@PathVariable Long userId) {
        return chatRoomService.getUserChatRooms(userId);
    }
}
