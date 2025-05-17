package com.example.digital_banking_backend.Repositories;

import com.example.digital_banking_backend.Entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByBankAccountId(String bankAccountId);
    Page<Operation> findByBankAccountId(String id,Pageable pageable);
}
