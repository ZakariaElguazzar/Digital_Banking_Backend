package com.example.digital_banking_backend.Services;


import com.example.digital_banking_backend.DTOs.CustomerDTO;
import com.example.digital_banking_backend.Entities.BankAccount;
import com.example.digital_banking_backend.Entities.CurrentAccount;
import com.example.digital_banking_backend.Entities.SavingAccount;
import com.example.digital_banking_backend.Exceptions.BalanceNotSufficientException;
import com.example.digital_banking_backend.Exceptions.BankAccountNotFoundException;
import com.example.digital_banking_backend.Exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    CustomerDTO getCustomer(String id) throws CustomerNotFoundException;
    void deleteCustomer(String id);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    CurrentAccount saveCurrentBankAccount(String customerId, double overDraft , double initialBalance) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(String customerId, double interestRate, double initialBalance) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    List<BankAccount> bankAccountList();
    BankAccount getBankAccount(String id) throws BankAccountNotFoundException;
    void debit(String accountId, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;


}
