package com.example.bankproject.Service;

import com.example.bankproject.Model.Account;
import com.example.bankproject.Model.Transaction;
import com.example.bankproject.Repository.AccountRepository;
import com.example.bankproject.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getTransactionBySenderId(Long id) {
        return transactionRepository.findBySenderId(id);
    }

    public List<Transaction> getTransactionByReceiverId(Long id) {
        return transactionRepository.findByReceiverId(id);
    }

    public void deleteTransactionById(Long id){
        if(transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException("Transaction with Id " + id + " does not exist");
        }
    }

    @Transactional
    public void transferAmount(Long senderId, Long receiverId, Double amount) {
        Account sender = accountRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Sender with ID " + senderId + " does not exist"));
        Account receiver = accountRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("Receiver with ID" + receiverId + " does not exist "));

        if(sender.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance in sender's account");
        }
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}
