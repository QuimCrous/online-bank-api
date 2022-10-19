package com.bankonline.Final_Project.controllers.users.interfaces;

import com.bankonline.Final_Project.DTOs.*;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.models.users.Admin;
import com.bankonline.Final_Project.models.users.ThirdPartyUser;
import com.bankonline.Final_Project.models.users.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AdminControllerInterface {
    Account modifyBalance(@RequestBody ModifyBalanceDTO modifyBalanceDTO);
    Account changeStatusAccount(@RequestBody AccountStatusDTO accountStatusDTO);
    Account createNewUserAccount(@RequestBody AccountHolderDTO accountHolderDTO);
    List<User> getAllUsers();
    String deleteAccount(@RequestBody Long id);
    Account createNewAccountByUser(@RequestBody CreateAccountDTO createAccountDTO);
    String addSecondaryOwner(@RequestBody AddSecondOwnerDTO addSecondOwnerDTO);
    List<Account> getAllAccounts();

    ThirdPartyUser createThirdPartyUser(@RequestBody ThirdPartyUser thirdPartyUser);

    AccountHolder addPassword(@RequestBody PasswordDTO passwordDTO);
    Admin createAdmin(@RequestBody AdminDTO adminDTO);
}
