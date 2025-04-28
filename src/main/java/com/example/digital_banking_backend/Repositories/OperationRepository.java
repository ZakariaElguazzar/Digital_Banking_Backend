package com.example.digital_banking_backend.Repositories;

import com.example.digital_banking_backend.Entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
