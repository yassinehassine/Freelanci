package com.example.freelanci.gestionPaiement.Controller;

import com.example.freelanci.gestionPaiement.Entity.Wallet;
import com.example.freelanci.gestionPaiement.Service.WalletService;
import com.example.freelanci.gestionUser.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    // Deposit money for a client
    @PostMapping("/deposit/{userId}")
    public Wallet depositMoney(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        return walletService.depositMoney(userId, amount);
    }

    // Withdraw money for a freelancer or admin
    @PostMapping("/withdraw/{userId}")
    public Wallet withdrawMoney(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        return walletService.withdrawMoney(userId, amount);
    }
    @GetMapping("/user/{userId}/account-balance")
    public BigDecimal getAccountBalance(@PathVariable Long userId) {
        return walletService.getAccountBalance(userId);
    }

}
