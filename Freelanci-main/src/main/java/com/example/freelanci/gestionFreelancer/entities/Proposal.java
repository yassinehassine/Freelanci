package com.example.freelanci.gestionFreelancer.entities;
import com.example.freelanci.gestionClient.entities.Job;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity

public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProposal;

    public Proposal(long idProposal) {
        this.idProposal = idProposal;
    }

    public Proposal() {
    }
    @ManyToOne
    @JoinColumn(name = "job_id") // Foreign key to Job
    private Job job;

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public long getDuration() {
        return Duration;
    }

    public void setDuration(long duration) {
        Duration = duration;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "idProposal=" + idProposal +
                ", Note='" + Note + '\'' +
                ", Price=" + Price +
                ", Duration=" + Duration +
                '}';
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public long getIdProposal() {
        return idProposal;
    }

    public void setIdProposal(long idProposal) {
        this.idProposal = idProposal;
    }

    private String Note;
    private long Price;
    private long Duration;
    private String Status; //accepted, declined , pending calcuul kadeh aandou pourcentage
    @ManyToOne
    private Freelancer freelancer;
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}
