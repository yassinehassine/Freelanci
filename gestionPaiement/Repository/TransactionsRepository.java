package com.example.freelanci.gestionPaiement.Repository;

import com.example.freelanci.gestionPaiement.Entity.Transactions;
import com.example.freelanci.gestionUser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findByClient(User client);
    List<Transactions> findByFreelancer(User freelancer);
    List<Transactions> findByAdmin(User admin);
}

