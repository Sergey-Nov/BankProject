package org.example.bankingapp.request;

import java.math.BigDecimal;

public class WithdrawRequest {
    private BigDecimal amount;
    private String pinCode;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}