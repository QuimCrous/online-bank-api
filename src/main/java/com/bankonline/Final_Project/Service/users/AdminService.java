package com.bankonline.Final_Project.Service.users;

import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AdminServiceInterface;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.accounts.*;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class AdminService implements AdminServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    public Account modifyBalance(Long accountId, BigDecimal amount){
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Incorrect Account id"));
        Money amount2;
        BigDecimal amount3;
        if (amount.compareTo(BigDecimal.ZERO) > 0){
            amount3 = account.getBalance().increaseAmount(amount);
        } else {
            amount3 = account.getBalance().decreaseAmount(amount);
        }
        amount2 = new Money(amount3);
        account.setBalance(amount2);
        return accountRepository.save(account);
    }

    public Account decreaseBalance(Long accountId, BigDecimal amount){
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Incorrect Account id"));
        Money amount2 = new Money(account.getBalance().decreaseAmount(amount));
        account.setBalance(amount2);
        return accountRepository.save(account);
    }

    public Account changeStatusAccount(Long accountId, String status){
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Incorrect Account id"));
        Status status1 = Status.valueOf(status);
        account.setStatus(status1);
        return accountRepository.save(account);
    }

    public Account createNewAccount(AccountHolderDTO accountHolderDTO, String accountType, Money initialBalance){
        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(),accountHolderDTO.getMail(),accountHolderDTO.getPhone(),accountHolderDTO.getBirthDate(),accountHolderDTO.getPrimaryAddress());
        String accountType2 = accountType.toLowerCase().replaceAll("\\W+", "");
        switch (accountType2){
            case "savingsaccount":
                SavingsAccount account = new SavingsAccount();
                account.setPrimaryOwner(accountHolder);
                if (initialBalance.getAmount().compareTo(account.getMinimumBalance().getAmount()) < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Initial Balance must be over 1000USD");
                account.setBalance(initialBalance);
                account.setCreationDate(LocalDate.now());
                return accountRepository.save(account);
            case "creditcard":
                CreditCard card = new CreditCard();
                card.setPrimaryOwner(accountHolder);
                card.setBalance(card.getCreditLimit());
                card.setLastInterestDay(LocalDate.now());
                return accountRepository.save(card);
            case "checkingaccount":
                if (accountHolder.getBirthDate().compareTo(LocalDate.now()) >= 24){
                    CheckingAccount account1 = new CheckingAccount();
                    account1.setPrimaryOwner(accountHolder);
                    if (initialBalance.getAmount().compareTo(account1.getMinimumBalance().getAmount()) < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Initial Balance must be over 250USD");
                    account1.setBalance(initialBalance);
                    account1.setCreationDate(LocalDate.now());
                    return accountRepository.save(account1);
                } else {
                    StudentCheckingAccount account1 = new StudentCheckingAccount();
                    account1.setPrimaryOwner(accountHolder);
                    account1.setCreationDate(LocalDate.now());
                    account1.setBalance(initialBalance);
                    return accountRepository.save(account1);
                }
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Account Type");

        }

    }

}
