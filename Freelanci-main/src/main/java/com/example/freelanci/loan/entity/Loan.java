package com.example.freelanci.loan.entity;

import com.example.freelanci.gestionUser.entities.User;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private double paidAmount;

    private double remainingAmount;

    private String purpose;

    private LocalDate requestDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    // Change from Freelancer to User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // No-argument constructor
    public Loan() {
    }

    // All-argument constructor
    public Loan(Long id, double amount, double paidAmount, double remainingAmount, String purpose, LocalDate requestDate, LoanStatus status, User user) {
        this.id = id;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.remainingAmount = remainingAmount;
        this.purpose = purpose;
        this.requestDate = requestDate;
        this.status = status;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Optional: toString method for debugging or logging purposes
    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", amount=" + amount +
                ", paidAmount=" + paidAmount +
                ", remainingAmount=" + remainingAmount +
                ", purpose='" + purpose + '\'' +
                ", requestDate=" + requestDate +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
