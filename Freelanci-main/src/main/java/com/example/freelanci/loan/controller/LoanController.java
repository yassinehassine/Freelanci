package com.example.freelanci.loan.controller;
import org.springframework.http.MediaType;
import com.example.freelanci.loan.entity.Loan;
import com.example.freelanci.loan.entity.LoanStatus;
import com.example.freelanci.loan.service.LoanPredictionService;
import com.example.freelanci.loan.service.LoanService;
import com.example.freelanci.loan.service.LoanSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanSimulationService simulationService;

    @Autowired
    private LoanPredictionService predictionService;

    // Get all loans
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    // Get a loan by ID
    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    // Create a new loan
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Loan createLoan(@RequestBody Loan loan) {
        if (loan.getUser() == null || loan.getUser().getId() == null) {
            throw new RuntimeException("User ID is required");
        }
        return loanService.createLoan(loan);
    }

    // Update an existing loan
    @PutMapping("/{id}")
    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan loanDetails) {
        return loanService.updateLoan(id, loanDetails);
    }

    // Delete a loan
    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }

    // Get loan history for a user with filters
    @GetMapping("/history")
    public List<Loan> getLoanHistory(
            @RequestParam Long userId,
            @RequestParam(required = false) LoanStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return loanService.getLoanHistory(userId, status, startDate, endDate);
    }

    // Make a repayment on a loan
    @PostMapping("/{id}/repay")
    public ResponseEntity<?> makeRepayment(@PathVariable Long id, @RequestParam double amount) {
        try {
            Loan updatedLoan = loanService.makeRepayment(id, amount);
            return ResponseEntity.ok(updatedLoan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get repayment data for a loan
    @GetMapping("/{id}/repayment-data")
    public Map<String, Double> getRepaymentData(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Map<String, Double> data = new HashMap<>();
        data.put("totalAmount", loan.getAmount());
        data.put("paidAmount", loan.getPaidAmount());
        data.put("remainingAmount", loan.getRemainingAmount());

        return data;
    }

    // Simulate repayment plan
    @GetMapping("/simulate")
    public Map<String, String> simulateRepayment(
            @RequestParam double loanAmount,
            @RequestParam double monthlyIncome,
            @RequestParam(defaultValue = "0.3") double repaymentRatio) {
        return simulationService.simulateRepayment(loanAmount, monthlyIncome, repaymentRatio);
    }

    // Recommend maximum loan amount based on income
    @GetMapping("/recommend")
    public ResponseEntity<String> recommendMaxLoan(@RequestParam double income) {
        double maxLoanAmount = predictionService.predictMaxLoanAmount(income);
        return ResponseEntity.ok("Based on your income, the maximum loan amount you can take is: $" + maxLoanAmount);
    }

    // Apply for a loan with validation against predicted maximum
    @PostMapping("/apply")
    public ResponseEntity<String> applyForLoan(
            @RequestParam double income,
            @RequestParam double requestedAmount) {
        double maxLoanAmount = predictionService.predictMaxLoanAmount(income);

        if (requestedAmount > maxLoanAmount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: The requested loan amount ($" + requestedAmount + ") exceeds the maximum allowed amount ($" + maxLoanAmount + ").");
        }

        return ResponseEntity.ok("Loan application successful! You have requested: $" + requestedAmount);
    }
}
