package org.example.bankingapp.controller;

import org.example.bankingapp.model.Account;
import org.example.bankingapp.model.Transaction;
import org.example.bankingapp.request.AccountRequest;
import org.example.bankingapp.request.TransferRequest;
import org.example.bankingapp.request.WithdrawRequest;
import org.example.bankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "account/index";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
        try {
            Account account = accountService.createAccount(request.getRecipientName(), request.getPinCode());
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/recipient")
    @ResponseBody
    public ResponseEntity<List<Account>> getAccountsByRecipientName(@RequestParam String recipientName) {
        return ResponseEntity.ok(accountService.getAccountsByRecipientName(recipientName));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        try {
            return accountService.getAccountById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{id}/deposit")
    @ResponseBody
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestBody BigDecimal amount) {
        try {
            accountService.deposit(id, amount);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{id}/withdraw")
    @ResponseBody
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

    @PostMapping("/{id}/transfer")
    @ResponseBody
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

    @GetMapping("/{id}/transactions")
    @ResponseBody
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(accountService.getTransactions(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/admin/all")
    @ResponseBody
    public ResponseEntity<List<Account>> getAllAccountsForAdmin() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}