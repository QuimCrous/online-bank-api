package com.bankonline.Final_Project.controllers.users.interfaces;

import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AddressDTO;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AccountHolderControllerInterface {
    String transferMoney(@RequestBody AccHolderTransferDTO accHolderTransferDTO);
    List<Account> getAccounts(@RequestBody Long id);
    AccountHolder createAccountHolder(@RequestBody AccountHolderDTO accountHolderDTO);
    String addPrimaryAddress(@RequestParam Long id, @RequestBody AddressDTO addressDTO);
    String addMailingAddress(@RequestParam Long id, @RequestBody AddressDTO addressDTO);
    String getBalance(@RequestBody Long accountId);
}
