package com.example.digital_banking_backend.DTOs;


import com.example.digital_banking_backend.Entities.BankAccount;
import com.example.digital_banking_backend.Enums.OperationType;
import lombok.Data;

import java.util.Date;

@Data
public class OperationDTO {
    private Long id;
    private Date Date;
    private double amount;
    private OperationType type;
    private BankAccount bankAccount;
}
