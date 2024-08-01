package org.example.bankingapp.service;

import org.example.bankingapp.model.Transaction;
import org.example.bankingapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransaction(Long accountId, BigDecimal amount, String type) {
        Transaction transaction = new Transaction(accountId, amount, type);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
