package com.bankonline.Final_Project.Service.users;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.CreateAccountDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AdminServiceInterface;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.accounts.*;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.models.users.User;
import com.bankonline.Final_Project.repositories.accounts.*;
import com.bankonline.Final_Project.repositories.users.AccountHolderRepository;
import com.bankonline.Final_Project.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class AdminService implements AdminServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    SavingAccountRepository savingAccountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    public Account modifyBalance(Long accountId, BigDecimal amount, String type){

        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Incorrect Account id"));
        BigDecimal amount2;
        if (type.equals("increase")){
            amount2 = account.getBalance().increaseAmount(amount);
            account.setBalance((amount2));
        } else if (type.equals("decrease")) {
            amount2 = account.getBalance().decreaseAmount(amount);
            account.setBalance((amount2));
        }
        return accountRepository.save(account);
    }


    public Account changeStatusAccount(Long accountId, String status){
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Incorrect Account id"));
        Status status1 = Status.valueOf(status);
        account.setStatus(status1);
        return accountRepository.save(account);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public String deleteAccount(Long id){
        Account account = accountRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Incorrect Account id"));
        String response = "The account with "+ account.getId() + " id has been deleted.";
        accountRepository.delete(account);
        return response;
    }
    public String addSecondaryOwner(Long secondId, Long accountId){
        AccountHolder accountHolder2 = accountHolderRepository.findById(secondId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        account.setSecondaryOwner(accountHolder2);
        accountRepository.save(account);
        return "The secondary owner has been updated";
    }
    public Account createNewAccount(AccountHolderDTO accountHolderDTO){
        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(),accountHolderDTO.getMail(),accountHolderDTO.getPhone(),accountHolderDTO.getBirthDate());
        userRepository.save(accountHolder);
        System.out.println(accountHolderDTO.getAccountType());
        return switch (accountHolderDTO.getAccountType()) {
            case "savingsaccount" ->
                    createSavingAccount(accountHolderDTO.getInitialBalance(), accountHolder, accountHolderDTO.getMinimumBalance(), accountHolderDTO.getInterestRate());
            case "creditcard" ->
                    createCreditCard(accountHolder, accountHolderDTO.getMinimumBalance(), accountHolderDTO.getInterestRate());
            case "checkingaccount" -> createCheckingAccount(accountHolderDTO.getInitialBalance(), accountHolder);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Account Type");
        };

    }

    public Account createNewAccountByUser(CreateAccountDTO createAccountDTO){
        AccountHolder accountHolder = accountHolderRepository.findById(createAccountDTO.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        return switch (createAccountDTO.getAccountType()) {
            case "savingsaccount" ->
                    createSavingAccount(createAccountDTO.getInitialBalance(), accountHolder, createAccountDTO.getMinimumBalance(), createAccountDTO.getInterestRate());
            case "creditcard" ->
                    createCreditCard(accountHolder, createAccountDTO.getMinimumBalance(), createAccountDTO.getInterestRate());
            case "checkingaccount" -> createCheckingAccount(createAccountDTO.getInitialBalance(), accountHolder);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Account Type");
        };

    }


    public Account createSavingAccount(BigDecimal initialBalance, AccountHolder accountHolder, BigDecimal minimumBalance, BigDecimal interestRate){
        SavingsAccount account = new SavingsAccount();
        account.setPrimaryOwner(accountHolder);
        account.setBalance(initialBalance);
        account.setMinimumBalance(minimumBalance);
        account.setInterestRate(interestRate);
        account.setCreationDate(LocalDate.now());
        account.setLastInterestRate(LocalDate.now());
        return savingAccountRepository.save(account);
    }
    public Account createCreditCard(AccountHolder accountHolder, BigDecimal minimumBalance, BigDecimal interestRate){
        CreditCard card = new CreditCard();
        card.setPrimaryOwner(accountHolder);
        card.setCreditLimit(minimumBalance);
        card.setBalance(minimumBalance);
        card.setInterestRate(interestRate);
        card.setCreationDate(LocalDate.now());
        card.setLastInterestDay(LocalDate.now());
        return creditCardRepository.save(card);
    }

    public Account createCheckingAccount(BigDecimal initialBalance, AccountHolder accountHolder){
        if (Period.between(accountHolder.getBirthDate(), LocalDate.now()).getYears() >= 24){
            CheckingAccount account1 = new CheckingAccount();
            account1.setPrimaryOwner(accountHolder);
            account1.setBalance(initialBalance);
            account1.setCreationDate(LocalDate.now());
            account1.setLastInterestDay(LocalDate.now());
            return checkingAccountRepository.save(account1);
        } else {
            StudentCheckingAccount account1 = new StudentCheckingAccount();
            account1.setPrimaryOwner(accountHolder);
            account1.setCreationDate(LocalDate.now());
            account1.setBalance(initialBalance);
            return studentCheckingAccountRepository.save(account1);
        }
    }
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

}
