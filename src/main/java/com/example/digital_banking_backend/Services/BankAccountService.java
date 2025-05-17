package com.example.digital_banking_backend.Services;


import com.example.digital_banking_backend.DTOs.*;
import com.example.digital_banking_backend.Exceptions.BalanceNotSufficientException;
import com.example.digital_banking_backend.Exceptions.BankAccountNotFoundException;
import com.example.digital_banking_backend.Exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    CustomerDTO getCustomer(String id) throws CustomerNotFoundException;
    void deleteCustomer(String id);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    CurrentAccountDTO saveCurrentBankAccount(String customerId, double overDraft , double initialBalance) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingBankAccount(String customerId, double interestRate, double initialBalance) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    List<BankAccountDTO> bankAccountList();
    BankAccountDTO getBankAccount(String id) throws BankAccountNotFoundException;
    void debit(String accountId, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<OperationDTO> AccountHistory(String id);
    AccountHistoryDTO  getAccountHistory(String id, int page, int size);
    List<CustomerDTO> searchCustomer(String keyword);
}
