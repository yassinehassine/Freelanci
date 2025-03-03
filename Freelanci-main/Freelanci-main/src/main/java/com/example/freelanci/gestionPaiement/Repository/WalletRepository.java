package com.example.freelanci.gestionPaiement.Repository;

import com.example.freelanci.gestionPaiement.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
