package org.example.bankingapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String recipientName;
    private String pinCode;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public Account(String recipientName, String pinCode) {
        this.recipientName = recipientName;
        this.pinCode = pinCode;
        this.accountNumber = UUID.randomUUID().toString();
        this.balance = BigDecimal.ZERO;
    }
}