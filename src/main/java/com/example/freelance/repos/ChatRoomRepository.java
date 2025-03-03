package com.example.freelance.repos;

import com.example.freelance.entites.ChatRoom;
import com.example.freelance.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByUser1AndUser2(User user1, User user2);



    List<ChatRoom> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);
}
