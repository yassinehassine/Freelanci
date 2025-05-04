package com.example.freelanci.freelance.entites;


import com.example.freelanci.gestionUser.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "messages")

@JsonIgnoreProperties({"chatRoom"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clé primaire
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;


    @Column(name = "seen", nullable = false)
    private boolean seen;

    // Jointure avec User (Expéditeur)
    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonBackReference("sentMessages")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonBackReference("receivedMessages")
    private User receiver;

    // Jointure avec ChatRoom (optionnel)
    @ManyToOne
    @JoinColumn(name = "chatroom_id", foreignKey = @ForeignKey(name = "fk_message_chatroom"))
    private ChatRoom chatRoom;

    // 🟢 Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}


