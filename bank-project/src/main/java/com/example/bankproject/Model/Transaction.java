package com.example.bankproject.Model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Accessors(chain = true)
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private LocalDateTime transactionDate;


    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "id",nullable = false)
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiverId", referencedColumnName = "id", nullable = false)
    private Account receiver;
}
