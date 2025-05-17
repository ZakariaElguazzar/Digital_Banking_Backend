package com.example.digital_banking_backend.Services;

import com.example.digital_banking_backend.DTOs.*;
import com.example.digital_banking_backend.Entities.*;
import com.example.digital_banking_backend.Enums.OperationType;
import com.example.digital_banking_backend.Enums.accStatus;
import com.example.digital_banking_backend.Exceptions.BalanceNotSufficientException;
import com.example.digital_banking_backend.Exceptions.BankAccountNotFoundException;
import com.example.digital_banking_backend.Exceptions.CustomerNotFoundException;
import com.example.digital_banking_backend.Mappers.BankAccountMapper;
import com.example.digital_banking_backend.Repositories.BankAccountRepository;
import com.example.digital_banking_backend.Repositories.CustomerRepository;
import com.example.digital_banking_backend.Repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private OperationRepository  operationRepository;
    private BankAccountMapper bankAccountMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        log.info("Saving customer: {}", customer.getName());
        Customer SavedCustomer = customerRepository.save(bankAccountMapper.fromCustomerDTO(customer));
        return bankAccountMapper.fromCustomer(SavedCustomer);
    }

    @Override
    public CustomerDTO getCustomer(String id) throws CustomerNotFoundException {
        Customer retrievedCustomer=customerRepository.findById(id).orElse(null);
        if (retrievedCustomer != null) {
            log.info("Customer found: {}", retrievedCustomer.getName());
            return bankAccountMapper.fromCustomer(retrievedCustomer);
        } else {
            log.warn("Customer not found with ID: {}", id);
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }
    }

    @Override
    public void deleteCustomer(String id) {
        log.info("Deleting customer with ID: {}", id);
        customerRepository.deleteById(id);

    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customer) {
        log.info("Updating customer: {}", customer.getName());
        Customer UpdatedCustomer = customerRepository.save(bankAccountMapper.fromCustomerDTO(customer));
        return bankAccountMapper.fromCustomer(UpdatedCustomer);
    }

    @Override
    public CurrentAccountDTO saveCurrentBankAccount(String customerId, double overDraft, double initialBalance) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            CurrentAccount currentAccount = new CurrentAccount();
            currentAccount.setCustomer(customer);
            currentAccount.setBalance(initialBalance);
            currentAccount.setCreatedAt(new java.util.Date());
            currentAccount.setStatus(accStatus.ACTIVATED);
            currentAccount.setCurrency("USD");
            currentAccount.setOverDraft(overDraft);
            CurrentAccount CurrentAccount = bankAccountRepository.save(currentAccount);
            log.info("Current account created for customer ID: {}", customerId);
            return bankAccountMapper.fromCurrentBankAccount(CurrentAccount);

        } else {
            log.error("Customer not found with ID: {}", customerId);
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }
    }

    @Override
    public SavingAccountDTO saveSavingBankAccount(String customerId, double interestRate, double initialBalance) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            SavingAccount savingAccount = new SavingAccount();
            savingAccount.setCustomer(customer);
            savingAccount.setBalance(initialBalance);
            savingAccount.setCreatedAt(new java.util.Date());
            savingAccount.setStatus(accStatus.ACTIVATED);
            savingAccount.setCurrency("USD");
            savingAccount.setInterestRate(interestRate);
            SavingAccount SavingAccount=bankAccountRepository.save(savingAccount);
            log.info("Saving account created for customer ID: {}", customerId);
            return bankAccountMapper.fromSavingBankAccount(SavingAccount);
        } else {
            log.error("Customer not found with ID: {}", customerId);
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        log.info("Listing all customers");
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customersDTO = customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).toList();
        for (Customer customer : customers) {
            log.info("Customer ID: {}, Name: {}", customer.getId(), customer.getName());
        }
        log.info("Total customers found: {}", customers.size());
        return customersDTO;
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream().map(bankAccount ->
        {
            if (bankAccount instanceof SavingAccount savingAccount) {
                return bankAccountMapper.fromSavingBankAccount(savingAccount);
            }
            else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String id) throws BankAccountNotFoundException {
        log.info("Fetching bank account with ID: {}", id);
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found with ID: " + id));
        if (bankAccount instanceof SavingAccount savingAccount) {
            return bankAccountMapper.fromSavingBankAccount(savingAccount);
        } else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found with ID: " + accountId));
        if (bankAccount.getStatus().equals(accStatus.ACTIVATED)) {
            if (bankAccount.getBalance() < amount)
                throw new BalanceNotSufficientException("Insufficient funds for account ID: " + accountId);
            Operation operation = new Operation();
            operation.setDate(new java.util.Date());
            operation.setAmount(amount);
            operation.setType(com.example.digital_banking_backend.Enums.OperationType.DEBIT);
            operation.setBankAccount(bankAccount);
            operationRepository.save(operation);
            log.info("Operation saved: {} of amount {} on account ID: {}", operation.getType(), amount, accountId);
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
            log.info("Debited {} from account ID: {}. New balance: {}", amount, accountId, bankAccount.getBalance());
        }
    }

    @Override
    public void credit(String accountId, double amount) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found with ID: " + accountId));
        Operation operation = new Operation();
        operation.setDate(new java.util.Date());
        operation.setAmount(amount);
        operation.setType(OperationType.CREDIT);
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
        log.info("Operation saved: {} of amount {} on account ID: {}", operation.getType(), amount, accountId);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
        log.info("Credited {} to account ID: {}. New balance: {}", amount, accountId, bankAccount.getBalance());
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource, amount);
        credit(accountIdDestination, amount);
        log.info("Transferred {} from account ID: {} to account ID: {}", amount, accountIdSource, accountIdDestination);
    }

    @Override
    public List<OperationDTO> AccountHistory(String id){
        log.info("Fetching account history for account ID: {}", id);
        return operationRepository.findByBankAccountId(id).stream().map(operation -> bankAccountMapper.fromOperation(operation)).toList();
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String id, int page, int size) {
        Page<Operation> operations = operationRepository.findByBankAccountId(id, PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<OperationDTO> operationsDTO = operations.getContent().stream().map(operation -> bankAccountMapper.fromOperation(operation)).toList();
        accountHistoryDTO.setOperationsDTO(operationsDTO);
        accountHistoryDTO.setAccountId(id);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(operations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomer(String keyword) {
        List<Customer> customers = customerRepository.search(keyword);
        return customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).toList();
    }
}
