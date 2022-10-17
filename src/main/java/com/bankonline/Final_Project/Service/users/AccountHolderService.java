package com.bankonline.Final_Project.Service.users;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AccountHolderServiceInterface;
import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountHolderService implements AccountHolderServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public String transferMoney(Long ownId, Long otherId, BigDecimal amount){
        Account ownAccount = accountRepository.findById(ownId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The owner account Id doesn't exist"));
        Account otherAccount = accountRepository.findById(otherId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The receptor account Id doesn't exist"));
        ownAccount.setBalance(new Money(ownAccount.getBalance().decreaseAmount(amount)));
        otherAccount.setBalance(new Money(otherAccount.getBalance().increaseAmount(amount)));
        accountRepository.saveAll(List.of(ownAccount, otherAccount));
        return ownAccount.getPrimaryOwner().getName() + " has transfer " + amount + " EUR to " + otherAccount.getPrimaryOwner().getName();
    }

    public List<Account> getAccounts(Long accountHolderId){
        List<Account> accountList = accountRepository.findByPrimaryOwner(accountHolderRepository.findById(accountHolderId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The user doesn't exist.")));
        accountList.addAll(accountRepository.findBySecondaryOwner(accountHolderRepository.findById(accountHolderId).get()));
        return accountList;
    }

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO){
        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(),accountHolderDTO.getMail(),accountHolderDTO.getPhone(),accountHolderDTO.getBirthDate());
        return accountHolderRepository.save(accountHolder);
    }

    public String addPrimaryAddress(Long id, Address address){
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        accountHolder.setPrimaryAddress(address);
        accountHolderRepository.save(accountHolder);
        return "The primary address has been updated";
    }

    public String addMailingAddress(Long id, Address address){
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        accountHolder.setMailingAddress(address);
        accountHolderRepository.save(accountHolder);
        return "The mailing address has been updated";
    }

    public String addSecondaryOwner(Long secondId, Long accountId){
        AccountHolder accountHolder2 = accountHolderRepository.findById(secondId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        account.setSecondaryOwner(accountHolder2);
        accountRepository.save(account);
        return "The secondary owner has been updated";
    }
}
