package com.bankonline.Final_Project.Service.users.interfaces;

import com.bankonline.Final_Project.models.accounts.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderServiceInterface {
    String transferMoney(Long ownId, Long otherId, BigDecimal amount);
    List<Account> getAccounts(Long accountHolderId);
}
