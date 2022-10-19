package com.bankonline.Final_Project.Service.users.interfaces;

import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AddressDTO;
import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderServiceInterface {
    Money transferMoneyByAccountType(String name, Long ownId, Long otherId, BigDecimal amount);
    List<Account> getAccounts(Long accountHolderId);
    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
    Address addPrimaryAddress(Long id, AddressDTO addressDTO);
    Address addMailingAddress(Long id, AddressDTO addressDTO);
    Money getBalance(Long id, String userName);
}
