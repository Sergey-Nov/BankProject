package org.example.bankingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.bankingapp.request.AccountRequest;
import org.example.bankingapp.request.TransferRequest;
import org.example.bankingapp.request.WithdrawRequest;
import org.example.bankingapp.model.Account;
import org.example.bankingapp.model.Transaction;
import org.example.bankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Controller", description = "API для управления банковскими счетами")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Operation(summary = "Создание учетной записи")
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
        try {
            Account account = accountService.createAccount(request.getRecipientName(), request.getPinCode());
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Получение всех счетов по имени получателя")
    @GetMapping
    public ResponseEntity<List<Account>> getAccountsByRecipientName(@RequestParam String recipientName) {
        return ResponseEntity.ok(accountService.getAccountsByRecipientName(recipientName));
    }

    @Operation(summary = "Получение счета по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        try {
            return accountService.getAccountById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Внесение средств на счет")
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestBody BigDecimal amount) {
        try {
            accountService.deposit(id, amount);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Снятие средств со счета")
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestBody WithdrawRequest request) {
        try {
            accountService.withdraw(id, request.getAmount(), request.getPinCode());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Перевод средств между счетами")
    @PostMapping("/{id}/transfer")
    public ResponseEntity<Void> transfer(@PathVariable Long id, @RequestBody TransferRequest request) {
        try {
            accountService.transfer(id, request.getToAccountId(), request.getAmount(), request.getPinCode());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Получение всех транзакций по ID счета")
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(accountService.getTransactions(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Получение всех счетов")
    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @Operation(summary = "Получение всех счетов (только для администраторов)")
    @GetMapping("/admin/all")
    public ResponseEntity<List<Account>> getAllAccountsForAdmin() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}