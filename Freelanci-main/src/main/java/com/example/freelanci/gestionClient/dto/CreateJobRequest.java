package com.example.freelanci.gestionClient.dto;

import java.time.LocalDate;

public class CreateJobRequest {
    private String title;
    private String description;
    private float budget;
    private String category;
    private Long clientId;  // <-- The ID of the user who is the client
    private LocalDate createdAt;  // Or you could use java.util.Date
    private LocalDate deadline;
    private String projectStatus; // Could be "NEST_PAS_DEBUTE", "EN_COURS", etc.

    // Getters and setters...
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
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public String getProjectStatus() {
        return projectStatus;
    }
    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
}
