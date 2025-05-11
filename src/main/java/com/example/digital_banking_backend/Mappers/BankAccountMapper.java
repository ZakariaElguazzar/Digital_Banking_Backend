package com.example.digital_banking_backend.Mappers;

import com.example.digital_banking_backend.DTOs.CustomerDTO;
import com.example.digital_banking_backend.Entities.Customer;

public interface BankAccountMapper {
    CustomerDTO fromCustomer(Customer customer);
    Customer fromCustomerDTO(CustomerDTO customerDTO);

}
