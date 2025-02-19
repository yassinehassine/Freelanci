package com.example.freelanci.gestionClient.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

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
    private String clientName;
    private Date createdAt;
    private Date deadline;

    private EtatProjet projectStatus; // Using the Enum for project status
    @OneToMany(cascade =CascadeType.ALL    ,mappedBy = "job" )
    private List<Review> reviews;
    // Enum for Project Status
    public enum EtatProjet {
        NEST_PAS_DEBUTE,
        EN_COURS,
        TERMINER
    }
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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
}
