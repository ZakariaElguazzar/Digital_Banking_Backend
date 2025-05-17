package com.example.digital_banking_backend.Controllers;

import com.example.digital_banking_backend.DTOs.CustomerDTO;
import com.example.digital_banking_backend.Exceptions.CustomerNotFoundException;
import com.example.digital_banking_backend.Services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
@CrossOrigin(allowedHeaders = "*",origins = "*",exposedHeaders = "*")
public class CustomerRestController {
    BankAccountService bankAccountService;
    @PreAuthorize( "hasAuthority('SCOPE_USER')")
    @GetMapping("/list_customer")
    public List<CustomerDTO> listCustomers(){
        return bankAccountService.listCustomers();
    }
    @PreAuthorize( "hasAuthority('SCOPE_USER')")
    @GetMapping("/customer/{id}")
    public CustomerDTO Customer(@PathVariable String id) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(id);
    }

    @PreAuthorize( "hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/save")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PreAuthorize( "hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/update/{id}")
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable String id){
        customerDTO.setId(id);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @PreAuthorize( "hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable String id){
        bankAccountService.deleteCustomer(id);
    }

    @PreAuthorize( "hasAuthority('SCOPE_USER')")
    @GetMapping("/search_customer")
    public List<CustomerDTO> Search(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomer(keyword);
    }





}
