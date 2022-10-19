package com.bankonline.Final_Project.controllers.users;

import com.bankonline.Final_Project.DTOs.*;
import com.bankonline.Final_Project.Service.users.interfaces.AdminServiceInterface;
import com.bankonline.Final_Project.controllers.users.interfaces.AdminControllerInterface;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.models.users.ThirdPartyUser;
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
    }/*test done*/
    @PatchMapping("/admin/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account changeStatusAccount(@RequestBody AccountStatusDTO accountStatusDTO){
        return adminServiceInterface.changeStatusAccount(accountStatusDTO.getId(), accountStatusDTO.getStatus());
    }/*test done*/
    @PostMapping("/admin/create-new-user-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewUserAccount(@RequestBody AccountHolderDTO accountHolderDTO){
        return adminServiceInterface.createNewAccount(accountHolderDTO);
    }/*test done*/

    @GetMapping("/admin/all-users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return adminServiceInterface.getAllUsers();
    }/*test done*/

    @DeleteMapping("/admin/delete-account")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAccount(@RequestBody Long id){
        return adminServiceInterface.deleteAccount(id);
    }/*test done*/

    @PostMapping("/admin/create-new-account-by-user")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewAccountByUser(@RequestBody CreateAccountDTO createAccountDTO){
        return adminServiceInterface.createNewAccountByUser(createAccountDTO);
    }/*test done*/
    @PutMapping("/admin/add-second-owner")
    @ResponseStatus(HttpStatus.OK)
    public String addSecondaryOwner(@RequestBody AddSecondOwnerDTO addSecondOwnerDTO){
        return adminServiceInterface.addSecondaryOwner(addSecondOwnerDTO.getAccountHolderId(), addSecondOwnerDTO.getAccountId());
    }/*test done*/
    @GetMapping("/admin/get-all-accounts")
    public List<Account> getAllAccounts(){
        return adminServiceInterface.getAllAccounts();
    }/*test done*/

    @PostMapping("/admin/create-third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdPartyUser createThirdPartyUser(@RequestBody ThirdPartyUser thirdPartyUser){
        return adminServiceInterface.createThirdPartyUser(thirdPartyUser.getName(), thirdPartyUser.getHashedKey());
    }

    @PutMapping("/admin/account-holder-password")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder addPassword(@RequestBody PasswordDTO passwordDTO){
        return adminServiceInterface.addPassword(passwordDTO.getUserId(), passwordDTO.getPassword());
    }

}
