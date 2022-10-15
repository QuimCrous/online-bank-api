package com.bankonline.Final_Project.Service.users.interfaces;

import com.bankonline.Final_Project.models.accounts.Account;

import java.math.BigDecimal;

public interface AdminServiceInterface {

    Account modifyBalance(Long accountId, BigDecimal amount);
}
