package org.example.bankingapp.repository;

import org.example.bankingapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByRecipientName(String recipientName);
}
