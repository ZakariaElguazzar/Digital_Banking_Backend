package com.example.digital_banking_backend.Repositories;

import com.example.digital_banking_backend.Entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
