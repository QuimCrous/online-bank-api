package com.bankonline.Final_Project.Service.users.interfaces;

import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AddressDTO;
import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderServiceInterface {
    String transferMoneyByAccountType(Long ownId, Long otherId, BigDecimal amount);
    List<Account> getAccounts(Long accountHolderId);
    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
    String addPrimaryAddress(Long id, AddressDTO addressDTO);
    String addMailingAddress(Long id, AddressDTO addressDTO);
    String getBalance(Long id);
}
