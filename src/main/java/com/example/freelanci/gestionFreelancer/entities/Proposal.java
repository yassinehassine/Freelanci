package com.example.freelanci.gestionFreelancer.entities;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;
import java.io.Serializable;
import java.util.Set;
import java.util.List;
import java.util.Date;

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
