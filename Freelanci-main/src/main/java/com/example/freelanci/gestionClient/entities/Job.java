package com.example.freelanci.gestionClient.entities;

import com.example.freelanci.gestionUser.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jobId;

    private String title;
    private String description;
    private float budget;
    private String category;

    // Foreign Key to User (client)
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id") // Relier à l'ID de User
    private User client; // C'est la relation qui lie le Job à un utilisateur

    private Date createdAt;
    private Date deadline;

    private EtatProjet projectStatus; // Enum pour l'état du projet

    @OneToMany(mappedBy = "job")
    private List<Review> reviews;

    // Enum pour le statut du projet
    public enum EtatProjet {
        NEST_PAS_DEBUTE,
        EN_COURS,
        TERMINER
    }

    // Getters et Setters
    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public EtatProjet getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(EtatProjet projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
