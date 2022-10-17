package com.bankonline.Final_Project.controllers.users;

import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AccountHolderServiceInterface;
import com.bankonline.Final_Project.controllers.users.interfaces.AccountHolderControllerInterface;
import com.bankonline.Final_Project.models.accounts.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountHolderController implements AccountHolderControllerInterface {

    @Autowired
    AccountHolderServiceInterface accountHolderServiceInterface;
    @PatchMapping("/accountholder/transfer")
    @ResponseStatus(HttpStatus.OK)
    public String transferMoney(@RequestBody AccHolderTransferDTO accHolderTransferDTO){
        return accountHolderServiceInterface.transferMoney(accHolderTransferDTO.getOwnId(), accHolderTransferDTO.getOtherId(), accHolderTransferDTO.getAmount());
    }
    @GetMapping("/accountholder")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Account> getAccounts(@RequestBody Long id){
        return accountHolderServiceInterface.getAccounts(id);
    }
}
