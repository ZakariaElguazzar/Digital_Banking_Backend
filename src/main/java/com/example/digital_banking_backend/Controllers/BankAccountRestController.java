package com.example.digital_banking_backend.Controllers;

import com.example.digital_banking_backend.DTOs.AccountHistoryDTO;
import com.example.digital_banking_backend.DTOs.BankAccountDTO;
import com.example.digital_banking_backend.Exceptions.BankAccountNotFoundException;
import com.example.digital_banking_backend.Services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank_accounts")
@AllArgsConstructor
@CrossOrigin(allowedHeaders = "*",origins = "*",exposedHeaders = "*")
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/bank_account/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }
    @GetMapping("/list_bank_accounts")
    public List<BankAccountDTO> listBankAccounts(){
        return bankAccountService.bankAccountList();
    }

    @GetMapping("{id}/operations")

    public List<com.example.digital_banking_backend.DTOs.OperationDTO> AccountHistory(@PathVariable String id){
        return bankAccountService.AccountHistory(id);
    }

    @GetMapping("/{id}/page_operations")

    public AccountHistoryDTO getAccountHistory(
            @PathVariable String id,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size)
    {
        return bankAccountService.getAccountHistory(id,page,size);
    }

}
