package com.example.freelanci.gestionPaiement.Service;

import com.example.freelanci.gestionPaiement.Entity.Transactions;
import com.example.freelanci.gestionPaiement.Entity.TransactionStatus;
import com.example.freelanci.gestionPaiement.Repository.TransactionsRepository;
import com.example.freelanci.gestionUser.entities.User;
import com.example.freelanci.gestionUser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private UserRepository userRepository;

    // Créer une transaction :
    // - Déduire le montant du solde du client
    // - Créditer l'admin avec le montant
    // - Créer la transaction avec le statut PENDING
    public Transactions createTransaction(Long clientId, Long freelancerId, BigDecimal amount) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer not found"));
        User admin = findAdminUser(); // Identification de l'unique admin

        // Vérifier que le client a suffisamment de solde
        if (client.getAccountBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Client does not have enough balance for this transaction");
        }

        // Déduire le montant du client
        client.setAccountBalance(client.getAccountBalance().subtract(amount));
        userRepository.save(client);

        // Créditer l'admin du montant complet
        admin.setAccountBalance(admin.getAccountBalance().add(amount));
        userRepository.save(admin);

        // Créer et sauvegarder la transaction en PENDING
        Transactions transaction = new Transactions();
        transaction.setAmountTransaction(amount);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setClient(client);
        transaction.setFreelancer(freelancer);
        transaction.setAdmin(admin);
        transaction.setDateTransaction(LocalDate.now());
        transaction.setTimeTransaction(LocalTime.now());

        return transactionsRepository.save(transaction);
    }

    // Mettre à jour le statut d'une transaction (ne peut être mise à jour que si en PENDING)
    public Transactions updateTransactionStatus(Long transactionId, TransactionStatus newStatus) {
        Transactions transaction = transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Autoriser la mise à jour seulement si le statut actuel est PENDING
        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Transaction status already updated");
        }

        User admin = transaction.getAdmin();
        BigDecimal amount = transaction.getAmountTransaction();

        if (newStatus == TransactionStatus.RELEASED) {
            // Transférer 95% du montant du solde de l'admin vers le freelancer (l'admin garde 5%)
            BigDecimal freelancerShare = amount.multiply(BigDecimal.valueOf(0.95));

            if (admin.getAccountBalance().compareTo(freelancerShare) < 0) {
                throw new RuntimeException("Admin does not have enough balance to transfer to freelancer");
            }

            // Déduire la part du freelancer de l'admin
            admin.setAccountBalance(admin.getAccountBalance().subtract(freelancerShare));
            userRepository.save(admin);

            // Créditer le freelancer avec 95% du montant
            User freelancer = transaction.getFreelancer();
            freelancer.setAccountBalance(freelancer.getAccountBalance().add(freelancerShare));
            userRepository.save(freelancer);

        } else if (newStatus == TransactionStatus.CANCELLED) {
            // Annulation : remboursement total du montant de l'admin vers le client
            if (admin.getAccountBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Admin does not have enough balance to refund the client");
            }
            admin.setAccountBalance(admin.getAccountBalance().subtract(amount));
            userRepository.save(admin);

            User client = transaction.getClient();
            client.setAccountBalance(client.getAccountBalance().add(amount));
            userRepository.save(client);
        }

        // Mettre à jour le statut de la transaction
        transaction.setStatus(newStatus);
        return transactionsRepository.save(transaction);
    }

    // Méthode utilitaire pour trouver l'unique admin dans la base
    private User findAdminUser() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getRole() == User.UserRole.ADMIN) {
                return user;
            }
        }
        throw new RuntimeException("Admin not found");
    }
}
