package com.example.freelanci.gestionPaiement.Entity;

import com.example.freelanci.gestionUser.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;

    private BigDecimal amountTransaction;

    private LocalDate dateTransaction;
    private LocalTime timeTransaction;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status; // 'PENDING', 'RELEASED', 'CANCELLED'

    @ManyToOne
    private User client; // L'utilisateur client qui initie la transaction

    @ManyToOne
    private User freelancer; // L'utilisateur freelancer qui reçoit la somme après validation

    @ManyToOne
    private User admin; // L'utilisateur admin qui reçoit 5% de la transaction
    public Long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public BigDecimal getAmountTransaction() {
        return amountTransaction;
    }

    public void setAmountTransaction(BigDecimal amountTransaction) {
        this.amountTransaction = amountTransaction;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public LocalTime getTimeTransaction() {
        return timeTransaction;
    }

    public void setTimeTransaction(LocalTime timeTransaction) {
        this.timeTransaction = timeTransaction;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(User freelancer) {
        this.freelancer = freelancer;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}

