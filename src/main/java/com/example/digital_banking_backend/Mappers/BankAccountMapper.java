package com.example.digital_banking_backend.Mappers;

import com.example.digital_banking_backend.DTOs.CurrentAccountDTO;
import com.example.digital_banking_backend.DTOs.CustomerDTO;
import com.example.digital_banking_backend.DTOs.OperationDTO;
import com.example.digital_banking_backend.DTOs.SavingAccountDTO;
import com.example.digital_banking_backend.Entities.CurrentAccount;
import com.example.digital_banking_backend.Entities.Customer;
import com.example.digital_banking_backend.Entities.Operation;
import com.example.digital_banking_backend.Entities.SavingAccount;

public interface BankAccountMapper {
    CustomerDTO fromCustomer(Customer customer);
    Customer fromCustomerDTO(CustomerDTO customerDTO);
    SavingAccountDTO fromSavingBankAccount(SavingAccount savingAccount);
    SavingAccount fromSavingBankAccountDTO(SavingAccountDTO savingAccountDTO);
    CurrentAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount);
    CurrentAccount fromCurrentBankAccountDTO(CurrentAccountDTO currentAccountDTO);
    OperationDTO fromOperation(Operation operation);
    Operation fromOperationDTO(OperationDTO operationDTO);
}
