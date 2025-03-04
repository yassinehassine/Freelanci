package com.example.freelanci.gestionPaiement.Service;

import com.example.freelanci.gestionPaiement.Entity.Wallet;
import com.example.freelanci.gestionPaiement.Repository.WalletRepository;
import com.example.freelanci.gestionUser.entities.User;
import com.example.freelanci.gestionUser.repositories.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
            String recipientNumber = "+216"+93701055;
            String messageBody = "Dear " + user.getUsername() + ",\nYour deposit of " + amount + " TND has been successfully completed.\nCurrent Balance : " + user.getAccountBalance() + " TND.\nThank you for your trust!";
            sendSMS(recipientNumber, messageBody);


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
                String recipientNumber = "+21693701055";
                String messageBody = "Dear " + user.getUsername() + ",\nYour withdrawal of " + amount + " TND has been successfully completed.\nCurrent Balance : " + user.getAccountBalance() + " TND.\nThank you for your loyalty!";
                sendSMS(recipientNumber, messageBody);


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

    public List<Wallet> getWalletHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return walletRepository.findByUser(user);
    }
    private static final String ACCOUNT_SID = "ACbb0e8297898b6c4cc1ad0dd8ba356bb4";
    private static final String AUTH_TOKEN = "3ace996fee64fab83c6d0b06fa8cf836";

    public void sendSMS(String recipientNumber, String messageBody) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new PhoneNumber(recipientNumber),
                            new PhoneNumber("+14638420119"),
                            messageBody)
                    .create();

            System.out.println("SMS sent successfully. SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du SMS : " + e.getMessage());
        }
    }
}
