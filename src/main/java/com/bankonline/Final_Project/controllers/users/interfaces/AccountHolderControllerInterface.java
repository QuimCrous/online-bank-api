package com.bankonline.Final_Project.controllers.users.interfaces;

import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AddressDTO;
import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AccountHolderControllerInterface {
    Money transferMoney(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AccHolderTransferDTO accHolderTransferDTO);
    List<Account> getAccounts(@RequestBody Long id);
//    AccountHolder createAccountHolder(@RequestBody AccountHolderDTO accountHolderDTO);
    Address addPrimaryAddress(@RequestBody AddressDTO addressDTO);
    Address addMailingAddress(@RequestBody AddressDTO addressDTO);
    Money getBalance( @AuthenticationPrincipal UserDetails userDetails, @RequestBody Long id);
}
