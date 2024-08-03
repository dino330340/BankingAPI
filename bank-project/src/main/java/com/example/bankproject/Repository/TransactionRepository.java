package com.example.bankproject.Repository;

import com.example.bankproject.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT o FROM Transaction o WHERE o.sender.id = :senderId")
    List<Transaction> findBySenderId (@Param("senderId")Long senderId);

    @Query("SELECT o FROM Transaction o WHERE o.receiver.id = :receiverId")
    List<Transaction> findByReceiverId (@Param("receiverId")Long receiverId);
}
