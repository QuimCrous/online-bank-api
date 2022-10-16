package com.bankonline.Final_Project.controllers.users;

import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AccountStatusDTO;
import com.bankonline.Final_Project.DTOs.ModifyBalanceDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AdminServiceInterface;
import com.bankonline.Final_Project.controllers.users.interfaces.AdminControllerInterface;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AdminController implements AdminControllerInterface {

    @Autowired
    AdminServiceInterface adminServiceInterface;

    @PutMapping("/accounts/modifybalance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account modifyBalance(@RequestBody ModifyBalanceDTO modifyBalanceDTO){
        return adminServiceInterface.modifyBalance(modifyBalanceDTO.getAccountId(), modifyBalanceDTO.getAmount(), modifyBalanceDTO.getType());
    }
    @PatchMapping("/accounts/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account changeStatusAccount(@RequestBody AccountStatusDTO accountStatusDTO){
        return adminServiceInterface.changeStatusAccount(accountStatusDTO.getId(), accountStatusDTO.getStatus());
    }
    @PostMapping("/createnewuseraccount")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewUserAccount(@RequestBody AccountHolderDTO accountHolderDTO){
        return adminServiceInterface.createNewAccount(accountHolderDTO);
    }

    @GetMapping("/allusers")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return adminServiceInterface.getAllUsers();
    }

    @DeleteMapping("/deleteaccount")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAccount(@RequestBody Long id){
        return adminServiceInterface.deleteAccount(id);
    }

    @PostMapping("/createnewaccountbyuser")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewAccountByUser(@RequestBody AccountHolderDTO accountHolderDTO){
        return adminServiceInterface.createNewAccountByUser(accountHolderDTO);
    }

}
