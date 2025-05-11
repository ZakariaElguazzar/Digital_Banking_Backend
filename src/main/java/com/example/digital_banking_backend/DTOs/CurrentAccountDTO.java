package com.example.digital_banking_backend.DTOs;
import com.example.digital_banking_backend.Entities.BankAccount;
import com.example.digital_banking_backend.Entities.Customer;
import com.example.digital_banking_backend.Enums.accStatus;
import lombok.Data;
import java.util.Date;


@Data
public class CurrentAccountDTO extends BankAccount {
    private String Id;
    private Date createdAt;
    private double balance;
    private accStatus status;
    private String currency;
    private Customer customer;
    private double overDraft;
}
