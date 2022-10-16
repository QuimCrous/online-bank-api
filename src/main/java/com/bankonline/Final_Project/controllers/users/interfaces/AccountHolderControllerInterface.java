package com.bankonline.Final_Project.controllers.users.interfaces;

import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.models.accounts.Account;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AccountHolderControllerInterface {
    String transferMoney(@RequestBody AccHolderTransferDTO accHolderTransferDTO);
    List<Account> getAccounts(@RequestBody Long id);
}
