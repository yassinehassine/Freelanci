package com.example.freelanci.gestionPaiement.Repository;

import com.example.freelanci.gestionPaiement.Entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
}

