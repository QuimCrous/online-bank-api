package com.bankonline.Final_Project.controllers.users;
import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AddressDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AccountHolderServiceInterface;
import com.bankonline.Final_Project.controllers.users.interfaces.AccountHolderControllerInterface;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountHolderController implements AccountHolderControllerInterface {

    @Autowired
    AccountHolderServiceInterface accountHolderServiceInterface;
    @PatchMapping("/account-holder/transfer")
    @ResponseStatus(HttpStatus.OK)
    public String transferMoney(@RequestBody AccHolderTransferDTO accHolderTransferDTO){
        return accountHolderServiceInterface.transferMoneyByAccountType(accHolderTransferDTO.getOwnId(), accHolderTransferDTO.getOtherId(), accHolderTransferDTO.getAmount());
    }
    @GetMapping("/account-holder")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Account> getAccounts(@RequestBody Long id){
        return accountHolderServiceInterface.getAccounts(id);
    }

    @PostMapping("/account-holder/create-user")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolderDTO accountHolderDTO){
        return accountHolderServiceInterface.createAccountHolder(accountHolderDTO);
    }

    @PutMapping("/account-holder/add-primary-address")
    @ResponseStatus(HttpStatus.OK)
    public String addPrimaryAddress(@RequestParam Long id, @RequestBody AddressDTO addressDTO){
        return accountHolderServiceInterface.addPrimaryAddress(id,addressDTO);
    }

    @PutMapping("/account-holder/add-mailing-address")
    @ResponseStatus(HttpStatus.OK)
    public String addMailingAddress(@RequestParam Long id, @RequestBody AddressDTO addressDTO){
        return accountHolderServiceInterface.addMailingAddress(id,addressDTO);
    }


    @GetMapping("/account-holder/get-balance")
    public String getBalance(@RequestBody Long accountId){
        return accountHolderServiceInterface.getBalance(accountId);
    }
}
