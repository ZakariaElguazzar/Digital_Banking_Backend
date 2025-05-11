package com.example.digital_banking_backend.Controllers;

import com.example.digital_banking_backend.DTOs.CustomerDTO;
import com.example.digital_banking_backend.Exceptions.CustomerNotFoundException;
import com.example.digital_banking_backend.Services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerRestController {
    BankAccountService bankAccountService;
    @GetMapping("/list_customer")
    public List<CustomerDTO> listCustomers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customer/{id}")
    public CustomerDTO listCustomers(@PathVariable String id) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(id);
    }

    @PostMapping("/save")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }



}
