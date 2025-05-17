package com.example.digital_banking_backend.DTOs;
import com.example.digital_banking_backend.Enums.accStatus;
import lombok.Data;
import java.util.Date;


@Data
public class CurrentAccountDTO extends BankAccountDTO {
    private String Id;
    private Date createdAt;
    private double balance;
    private accStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
