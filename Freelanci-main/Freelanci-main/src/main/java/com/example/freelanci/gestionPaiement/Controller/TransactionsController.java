package com.example.freelanci.gestionPaiement.Controller;

import com.example.freelanci.gestionPaiement.Entity.Transactions;
import com.example.freelanci.gestionPaiement.Entity.TransactionStatus;
import com.example.freelanci.gestionPaiement.Service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    // Créer une transaction (client lance une transaction)
    @PostMapping("/create/{clientId}/{freelancerId}")
    public Transactions createTransaction(@PathVariable Long clientId, @PathVariable Long freelancerId, @RequestBody BigDecimal amount) {
        return transactionsService.createTransaction(clientId, freelancerId, amount);
    }

    // Mettre à jour le statut d'une transaction (RELEASED ou CANCELLED)
    @PostMapping("/update-status/{transactionId}")
    public Transactions updateTransactionStatus(@PathVariable Long transactionId, @RequestBody TransactionStatus status) {
        return transactionsService.updateTransactionStatus(transactionId, status);
    }
}
