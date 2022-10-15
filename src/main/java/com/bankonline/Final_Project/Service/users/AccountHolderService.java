package com.bankonline.Final_Project.Service.users;

import com.bankonline.Final_Project.Service.accounts.interfaces.AccountServiceInterface;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountHolderService implements AccountServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    public void transferMoney(Long ownId, Long otherId, BigDecimal amount){
        Account ownAccount = accountRepository.findById(ownId).orElseThrow();
        Account otherAccount = accountRepository.findById(otherId).orElseThrow();
        ownAccount.getBalance().decreaseAmount(amount);
        otherAccount.getBalance().increaseAmount(amount);
        accountRepository.saveAll(List.of(ownAccount, otherAccount));
    }
}
