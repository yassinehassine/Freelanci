package com.example.freelanci.freelance.repos;

import com.example.freelanci.freelance.entites.Message;
import com.example.freelanci.gestionUser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiver(User sender, User receiver);
}
