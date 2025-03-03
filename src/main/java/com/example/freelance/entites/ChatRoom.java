package com.example.freelance.entites;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "chat_rooms", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user1_id", "user2_id"})
})

public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "chat_id", nullable = false, unique = true)
    private String chatId; // Ex: "user1-user2"

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false, foreignKey = @ForeignKey(name = "fk_chatroom_user1"))
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false, foreignKey = @ForeignKey(name = "fk_chatroom_user2"))
    private User user2;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    // 🟢 Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    // 🟢 Méthode @PrePersist pour générer le chatId avant la persistance
    @PrePersist
    protected void onCreate() {
        if (chatId == null) {
            chatId = generateChatId(user1.getId(), user2.getId());
        }
    }

    // 🟢 Génération du chatId en fonction des ids des utilisateurs
    private String generateChatId(Long user1Id, Long user2Id) {
        return (user1Id < user2Id) ? user1Id + "-" + user2Id : user2Id + "-" + user1Id;
    }
}