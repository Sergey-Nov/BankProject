package org.example.bankingapp.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransferRequest {
    private Long toAccountId;
    private BigDecimal amount;
    private String pinCode;
}