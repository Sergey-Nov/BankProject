package org.example.bankingapp.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountRequest {
    private String recipientName;
    private String pinCode;
}