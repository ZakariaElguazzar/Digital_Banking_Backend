package com.example.digital_banking_backend.Entities;


import com.example.digital_banking_backend.Enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

// This class represents an operation entity in the banking system

public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date Date;
    private double amount;
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;

}
