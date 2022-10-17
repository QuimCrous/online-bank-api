package com.bankonline.Final_Project.Service.users;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.CreateAccountDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AdminServiceInterface;
import com.bankonline.Final_Project.embedables.Money;
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
            account.setBalance(new Money(amount2));
        } else if (type.equals("decrease")) {
            amount2 = account.getBalance().decreaseAmount(amount);
            account.setBalance(new Money(amount2));
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
        Money initialBalance = new Money(accountHolderDTO.getInitialBalance());
        switch (accountHolderDTO.getAccountType()){
            case "savingsaccount":
                return createSavingAccount(initialBalance, accountHolder);
            case "creditcard":
                return createCreditCard(initialBalance, accountHolder);
            case "checkingaccount":
                return createCheckingAccount(initialBalance,accountHolder);
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Account Type holiwi de kiwi");

        }

    }

    public Account createNewAccountByUser(CreateAccountDTO createAccountDTO){
        AccountHolder accountHolder = accountHolderRepository.findById(createAccountDTO.getId()).get();
        Money initialBalance = new Money(createAccountDTO.getInitialBalance());
        switch (createAccountDTO.getAccountType()){
            case "savingsaccount":
                return createSavingAccount(initialBalance, accountHolder);
            case "creditcard":
                return createCreditCard(initialBalance, accountHolder);
            case "checkingaccount":
                return createCheckingAccount(initialBalance, accountHolder);
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Account Type");

        }

    }


    public Account createSavingAccount(Money initialBalance, AccountHolder accountHolder){
        SavingsAccount account = new SavingsAccount();
        account.setPrimaryOwner(accountHolder);
        if (initialBalance.getAmount().compareTo(account.getMinimumBalance().getAmount()) < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Initial Balance must be over 1000EUR");
        account.setBalance(initialBalance);
        account.setCreationDate(LocalDate.now());
        account.setLastInterestRate(LocalDate.now());
        return savingAccountRepository.save(account);
    }
    public Account createCreditCard(Money initialBalance, AccountHolder accountHolder){
        CreditCard card = new CreditCard();
        card.setPrimaryOwner(accountHolder);
        card.setBalance(card.getCreditLimit());
        card.setCreationDate(LocalDate.now());
        card.setLastInterestDay(LocalDate.now());
        return creditCardRepository.save(card);
    }

    public Account createCheckingAccount(Money initialBalance, AccountHolder accountHolder){
        if (LocalDate.now().minusYears(24).compareTo(accountHolder.getBirthDate()) >= 0){
            CheckingAccount account1 = new CheckingAccount();
            account1.setPrimaryOwner(accountHolder);
            if (initialBalance.getAmount().compareTo(account1.getMinimumBalance().getAmount()) < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Initial Balance must be over 250EUR");
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
