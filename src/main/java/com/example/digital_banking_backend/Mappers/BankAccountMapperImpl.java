package com.example.digital_banking_backend.Mappers;

import com.example.digital_banking_backend.DTOs.CurrentAccountDTO;
import com.example.digital_banking_backend.DTOs.CustomerDTO;
import com.example.digital_banking_backend.DTOs.OperationDTO;
import com.example.digital_banking_backend.DTOs.SavingAccountDTO;
import com.example.digital_banking_backend.Entities.CurrentAccount;
import com.example.digital_banking_backend.Entities.Customer;
import com.example.digital_banking_backend.Entities.Operation;
import com.example.digital_banking_backend.Entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl implements BankAccountMapper {
    @Override
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    @Override
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    @Override
    public SavingAccountDTO fromSavingBankAccount(SavingAccount savingAccount) {
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        savingAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDTO;
    }

    @Override
    public SavingAccount fromSavingBankAccountDTO(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    @Override
    public CurrentAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        currentAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDTO;
    }

    @Override
    public CurrentAccount fromCurrentBankAccountDTO(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
        return currentAccount;
    }

    @Override
    public OperationDTO fromOperation(Operation operation){
        OperationDTO operationDTO = new OperationDTO();
        BeanUtils.copyProperties(operation, operationDTO);
        return  operationDTO;
    }

    @Override
    public Operation fromOperationDTO(OperationDTO operationDTO){
        Operation operation = new Operation();
        BeanUtils.copyProperties(operationDTO, operation);
        return  operation;
    }

}
