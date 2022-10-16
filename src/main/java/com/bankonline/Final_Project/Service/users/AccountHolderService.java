package com.bankonline.Final_Project.Service.users;

import com.bankonline.Final_Project.Service.accounts.interfaces.AccountServiceInterface;
import com.bankonline.Final_Project.Service.users.interfaces.AccountHolderServiceInterface;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountHolderService implements AccountHolderServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public String transferMoney(Long ownId, Long otherId, BigDecimal amount){
        Account ownAccount = accountRepository.findById(ownId).get();
        Account otherAccount = accountRepository.findById(otherId).get();
        ownAccount.setBalance(new Money(ownAccount.getBalance().decreaseAmount(amount)));
        otherAccount.setBalance(new Money(otherAccount.getBalance().increaseAmount(amount)));
        accountRepository.saveAll(List.of(ownAccount, otherAccount));
        return ownAccount.getPrimaryOwner().getName() + " has transfer " + amount + " EUR to " + otherAccount.getPrimaryOwner().getName();
    }

    public List<Account> getAccounts(Long accountHolderId){
        return accountRepository.findByPrimaryOwner(accountHolderRepository.findById(accountHolderId).get());
    }
}
