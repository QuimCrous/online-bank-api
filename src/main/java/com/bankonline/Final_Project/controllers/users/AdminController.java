package com.bankonline.Final_Project.controllers.users;

import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AccountStatusDTO;
import com.bankonline.Final_Project.DTOs.CreateAccountDTO;
import com.bankonline.Final_Project.DTOs.ModifyBalanceDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AdminServiceInterface;
import com.bankonline.Final_Project.controllers.users.interfaces.AdminControllerInterface;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class AdminController implements AdminControllerInterface {

    @Autowired
    AdminServiceInterface adminServiceInterface;

    @PatchMapping("/admin/modify-balance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account modifyBalance(@RequestBody ModifyBalanceDTO modifyBalanceDTO){
        return adminServiceInterface.modifyBalance(modifyBalanceDTO.getAccountId(), modifyBalanceDTO.getAmount(), modifyBalanceDTO.getType());
    }
    @PatchMapping("/admin/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account changeStatusAccount(@RequestBody AccountStatusDTO accountStatusDTO){
        return adminServiceInterface.changeStatusAccount(accountStatusDTO.getId(), accountStatusDTO.getStatus());
    }
    @PostMapping("/admin/create-new-user-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewUserAccount(@RequestBody AccountHolderDTO accountHolderDTO){
        return adminServiceInterface.createNewAccount(accountHolderDTO);
    }

    @GetMapping("/admin/all-users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return adminServiceInterface.getAllUsers();
    }

    @DeleteMapping("/admin/delete-account")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAccount(@RequestBody Long id){
        return adminServiceInterface.deleteAccount(id);
    }

    @PostMapping("/admin/create-new-account-by-user")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewAccountByUser(@RequestBody CreateAccountDTO createAccountDTO){
        return adminServiceInterface.createNewAccountByUser(createAccountDTO);
    }
    @PutMapping("/account-holder/add-second-owner")
    @ResponseStatus(HttpStatus.OK)
    public String addSecondaryOwner(@RequestParam Long ownId, @RequestBody Long otherId){
        return adminServiceInterface.addSecondaryOwner(ownId,otherId);
    }
    @GetMapping("/admin/get-all-accounts")
    public List<Account> getAllAccounts(){
        return adminServiceInterface.getAllAccounts();
    }

}
