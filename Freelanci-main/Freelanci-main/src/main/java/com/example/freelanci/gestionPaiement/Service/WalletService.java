package com.example.freelanci.gestionPaiement.Service;

import com.example.freelanci.gestionPaiement.Entity.Wallet;
import com.example.freelanci.gestionPaiement.Repository.WalletRepository;
import com.example.freelanci.gestionUser.entities.User;
import com.example.freelanci.gestionUser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public Wallet depositMoney(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() == User.UserRole.CLIENT) {
            user.setAccountBalance(user.getAccountBalance().add(amount));
            userRepository.save(user);

            Wallet wallet = new Wallet();
            wallet.setUser(user);
            wallet.setAmount(amount);
            wallet.setDate(LocalDate.now());
            wallet.setTime(LocalTime.now());

            return walletRepository.save(wallet);
        }
        throw new RuntimeException("Only clients can deposit money");
    }

    public Wallet withdrawMoney(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() == User.UserRole.FREELANCER || user.getRole() == User.UserRole.ADMIN) {
            if (user.getAccountBalance().compareTo(amount) >= 0) {
                user.setAccountBalance(user.getAccountBalance().subtract(amount));
                userRepository.save(user);

                Wallet wallet = new Wallet();
                wallet.setUser(user);
                wallet.setAmount(amount);
                wallet.setDate(LocalDate.now());
                wallet.setTime(LocalTime.now());

                return walletRepository.save(wallet);
            }
            throw new RuntimeException("Insufficient balance for withdrawal");
        }
        throw new RuntimeException("Only freelancers or admins can withdraw money");
    }
    public BigDecimal getAccountBalance(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAccountBalance();
    }


}
