package com.example.digital_banking_backend;

import com.example.digital_banking_backend.DTOs.BankAccountDTO;
import com.example.digital_banking_backend.DTOs.CurrentAccountDTO;
import com.example.digital_banking_backend.DTOs.CustomerDTO;
import com.example.digital_banking_backend.DTOs.SavingAccountDTO;
import com.example.digital_banking_backend.Exceptions.CustomerNotFoundException;
import com.example.digital_banking_backend.Services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackendApplication.class, args);
    }

    //@Bean
    //CommandLineRunner commandLineRunner(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, OperationRepository operationRepository) {
    //return args -> {
    // Create a new customer and save it to the database
//            Customer customer = new Customer();
//            customer.setName("John Doe");
//            customer.setEmail("john@gmail.com");
//            Customer Customer1 = customerRepository.save(customer);
//            SavingAccount bankAccount = new SavingAccount();
//            bankAccount.setCustomer(Customer1);
//            bankAccount.setBalance(1000.0);
//            bankAccount.setCreatedAt(new java.util.Date());
//            bankAccount.setStatus(accStatus.ACTIVATED);
//            bankAccount.setCurrency("USD");
//            bankAccount.setInterestRate(0.05);
//            BankAccount bankAccount1=bankAccountRepository.save(bankAccount);
//            Operation operation = new Operation();
//            operation.setDate(new java.util.Date());
//            operation.setAmount(1000.0);
//            operation.setType(com.example.digital_banking_backend.Enums.OperationType.CREDIT);
//            operation.setBankAccount(bankAccount1);
//            operationRepository.save(operation);


//            Customer customerS = new Customer();
//            customerS.setName("John Doe");
//            customerS.setEmail("doe@gmail.com");
//            Customer Customer1S = customerRepository.save(customerS);
//            CurrentAccount bankAccountS = new CurrentAccount();
//            bankAccountS.setCustomer(Customer1S);
//            bankAccountS.setBalance(1000.0);
//            bankAccountS.setCreatedAt(new java.util.Date());
//            bankAccountS.setStatus(accStatus.ACTIVATED);
//            bankAccountS.setCurrency("USD");
//            bankAccountS.setOverDraft(0.0523);
//            BankAccount bankAccount1S=bankAccountRepository.save(bankAccountS);
//            Operation operationS = new Operation();
//            operationS.setDate(new java.util.Date());
//            operationS.setAmount(1000.0);
//            operationS.setType(com.example.digital_banking_backend.Enums.OperationType.CREDIT);
//            operationS.setBankAccount(bankAccount1S);
//            operationRepository.save(operationS);


    //BankAccount bankaccount2 = bankAccountRepository.findById("3931e218-2bd5-42d9-9d71-92b8f7dc9e14").orElse(null);
    //if ( bankaccount2 !=null)
    //{
    //System.out.println("****************************");
    //System.out.println(bankaccount2.getCreatedAt());
    //System.out.println(bankaccount2.getCustomer().getName());
    //System.out.println(bankaccount2.getCustomer().getEmail());
    //System.out.println(bankaccount2.getCurrency());
    //System.out.println(bankaccount2.getStatus());
    //System.out.println(bankaccount2.getClass().getSimpleName());

//                if(bankaccount2 instanceof SavingAccount){
//                    System.out.println("Interest Rate");
//                    System.out.println(((SavingAccount) bankaccount2).getInterestRate());
//                }
//                if(bankaccount2 instanceof CurrentAccount){
//                    System.out.println("OverDraft");
//                    System.out.println(((CurrentAccount) bankaccount2).getOverDraft());
//                }
    //System.out.println("****************************");
    //}

    //};

    //}

    //@Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Ayoub", "Hiba", "Soukaina","Noura","Zakaria").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                Random rand = new Random();
                int randomNum = rand.nextInt(20000);
                String[] domains = {"gmail.com", "yahoo.com", "outlook.com", "protonmail.com","hotmail.com"};
                String randomDomain = domains[rand.nextInt(domains.length)];
                String email = name.toLowerCase().replaceAll("\\s+", "") + randomNum + "@" + randomDomain;
                customer.setEmail(email);
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(customer.getId(), 9000, Math.random() * 90000);
                    bankAccountService.saveSavingBankAccount(customer.getId(), 5.5, Math.random() * 120000);

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });

            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i <10 ; i++) {
                    String accountId;
                    if (bankAccount instanceof SavingAccountDTO savingAccountDTO) {
                        accountId = savingAccountDTO.getId();

                    } else {
                        CurrentAccountDTO currentAccountDTO = (CurrentAccountDTO) bankAccount;
                        accountId = currentAccountDTO.getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000);
                    bankAccountService.debit(accountId,1000+Math.random()*9000);

                }
            }


        };
    }
}
