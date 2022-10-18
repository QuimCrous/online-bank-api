package com.bankonline.Final_Project.controllers.users;
import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AddressDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AccountHolderServiceInterface;
import com.bankonline.Final_Project.controllers.users.interfaces.AccountHolderControllerInterface;
import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.embedables.Money;
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
    @PutMapping("/account-holder/transfer")
    @ResponseStatus(HttpStatus.OK)
    public Money transferMoney(@RequestBody AccHolderTransferDTO accHolderTransferDTO){
        return accountHolderServiceInterface.transferMoneyByAccountType(accHolderTransferDTO.getOwnId(), accHolderTransferDTO.getOtherId(), accHolderTransferDTO.getAmount());
    }/*test done*/
    @GetMapping("/account-holder")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Account> getAccounts(@RequestBody Long id){
        return accountHolderServiceInterface.getAccounts(id);
    }/*test done*/

    @PostMapping("/account-holder/create-user")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolderDTO accountHolderDTO){
        return accountHolderServiceInterface.createAccountHolder(accountHolderDTO);
    }

    @PutMapping("/account-holder/add-primary-address")
    @ResponseStatus(HttpStatus.OK)
    public Address addPrimaryAddress(@RequestBody AddressDTO addressDTO){
        return accountHolderServiceInterface.addPrimaryAddress(addressDTO.getId(), addressDTO);
    }

    @PutMapping("/account-holder/add-mailing-address")
    @ResponseStatus(HttpStatus.OK)
    public Address addMailingAddress(@RequestBody AddressDTO addressDTO){
        return accountHolderServiceInterface.addMailingAddress(addressDTO.getId(), addressDTO);
    }/*test done*/


    @GetMapping("/account-holder/get-balance")
    public Money getBalance(@RequestBody Long id){
        return accountHolderServiceInterface.getBalance(id);
    }/*test done*/
}
