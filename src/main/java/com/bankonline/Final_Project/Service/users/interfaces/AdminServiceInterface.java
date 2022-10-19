package com.bankonline.Final_Project.Service.users.interfaces;

import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.CreateAccountDTO;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.models.users.ThirdPartyUser;
import com.bankonline.Final_Project.models.users.User;

import java.math.BigDecimal;
import java.util.List;

public interface AdminServiceInterface {

    Account modifyBalance(Long accountId, BigDecimal amount, String type);
    Account changeStatusAccount(Long accountId, String status);
    Account createNewAccount(AccountHolderDTO accountHolderDTO);
    List<User> getAllUsers();
    String deleteAccount(Long id);
    Account createNewAccountByUser(CreateAccountDTO createAccountDTO);
    String addSecondaryOwner(Long secondId, Long accountId);
    List<Account> getAllAccounts();
    ThirdPartyUser createThirdPartyUser(String name, String hashedKey);
    AccountHolder addPassword(Long userId, String password);
}
