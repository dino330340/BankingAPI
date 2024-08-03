package com.example.bankproject.Controller;

import com.example.bankproject.Model.Transaction;
import com.example.bankproject.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transferAmount(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam Double amount) {
        try {
            transactionService.transferAmount(senderId, receiverId, amount);
            return ResponseEntity.ok("Transaction successful.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTransactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactionList = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactionList);
    }

    @GetMapping("/getTransactionById/{id}")
    public Optional<ResponseEntity<Transaction>> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return Optional.of(transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping("/by-sender/{senderId}")
    public ResponseEntity<List<Transaction>> getTransactionBySenderId(@PathVariable Long senderId){
        List<Transaction> transactionList = transactionService.getTransactionBySenderId(senderId);
        return ResponseEntity.ok(transactionList);
    }

    @GetMapping("/by-receiver/{receiverId}")
    public ResponseEntity<List<Transaction>> getTransactionByReceiverId(@PathVariable Long receiverId) {
        List<Transaction> transactionList = transactionService.getTransactionByReceiverId(receiverId);
        return ResponseEntity.ok(transactionList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransactionById(@PathVariable Long id ) {
        try {
            transactionService.deleteTransactionById(id);
            return ResponseEntity.ok("Transaction deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
