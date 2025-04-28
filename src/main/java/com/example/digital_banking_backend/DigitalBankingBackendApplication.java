package com.example.digital_banking_backend;

import com.example.digital_banking_backend.Entities.*;
import com.example.digital_banking_backend.Enums.accStatus;
import com.example.digital_banking_backend.Repositories.BankAccountRepository;
import com.example.digital_banking_backend.Repositories.CustomerRepository;
import com.example.digital_banking_backend.Repositories.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DigitalBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, OperationRepository operationRepository) {
        return args -> {
            // Create a new customer and save it to the database
            Customer customer = new Customer();
            customer.setName("John Doe");
            customer.setEmail("john@gmail.com");
            Customer Customer1 = customerRepository.save(customer);
            SavingAccount bankAccount = new SavingAccount();
            bankAccount.setCustomer(Customer1);
            bankAccount.setBalance(1000.0);
            bankAccount.setCreatedAt(new java.util.Date());
            bankAccount.setStatus(accStatus.ACTIVATED);
            bankAccount.setCurrency("USD");
            bankAccount.setInterestRate(0.05);
            BankAccount bankAccount1=bankAccountRepository.save(bankAccount);
            Operation operation = new Operation();
            operation.setDate(new java.util.Date());
            operation.setAmount(1000.0);
            operation.setType(com.example.digital_banking_backend.Enums.OperationType.CREDIT);
            operation.setBankAccount(bankAccount1);
            operationRepository.save(operation);


            Customer customerS = new Customer();
            customerS.setName("John Doe");
            customerS.setEmail("doe@gmail.com");
            Customer Customer1S = customerRepository.save(customerS);
            CurrentAccount bankAccountS = new CurrentAccount();
            bankAccountS.setCustomer(Customer1S);
            bankAccountS.setBalance(1000.0);
            bankAccountS.setCreatedAt(new java.util.Date());
            bankAccountS.setStatus(accStatus.ACTIVATED);
            bankAccountS.setCurrency("USD");
            bankAccountS.setOverDraft(0.0523);
            BankAccount bankAccount1S=bankAccountRepository.save(bankAccountS);
            Operation operationS = new Operation();
            operationS.setDate(new java.util.Date());
            operationS.setAmount(1000.0);
            operationS.setType(com.example.digital_banking_backend.Enums.OperationType.CREDIT);
            operationS.setBankAccount(bankAccount1S);
            operationRepository.save(operationS);

        };

    }

}
