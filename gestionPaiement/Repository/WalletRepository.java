package com.example.freelanci.gestionPaiement.Repository;

import com.example.freelanci.gestionPaiement.Entity.Wallet;
import com.example.freelanci.gestionUser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findByUser(User user);
}
