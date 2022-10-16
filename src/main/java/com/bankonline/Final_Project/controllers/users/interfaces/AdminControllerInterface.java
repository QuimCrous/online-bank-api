package com.bankonline.Final_Project.controllers.users.interfaces;

import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AccountStatusDTO;
import com.bankonline.Final_Project.DTOs.ModifyBalanceDTO;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdminControllerInterface {
    Account modifyBalance(@RequestBody ModifyBalanceDTO modifyBalanceDTO);
    Account changeStatusAccount(@RequestBody AccountStatusDTO accountStatusDTO);
    Account createNewUserAccount(@RequestBody AccountHolderDTO accountHolderDTO);
    List<User> getAllUsers();
    String deleteAccount(@RequestBody Long id);
    Account createNewAccountByUser(@RequestBody AccountHolderDTO accountHolderDTO);
}
