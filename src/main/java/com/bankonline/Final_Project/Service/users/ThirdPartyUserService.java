package com.bankonline.Final_Project.Service.users;

import com.bankonline.Final_Project.Service.users.interfaces.ThirdPartyUserServiceInterface;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.accounts.CheckingAccountRepository;
import com.bankonline.Final_Project.repositories.accounts.SavingAccountRepository;
import com.bankonline.Final_Project.repositories.users.AccountHolderRepository;
import com.bankonline.Final_Project.repositories.users.ThirdPartyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class ThirdPartyUserService implements ThirdPartyUserServiceInterface {


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ThirdPartyUserRepository thirdPartyUserRepository;


    public Money transferMoney(String hashKey, Long ownId, Integer secretKey, BigDecimal amount, String transferType){
        if (!thirdPartyUserRepository.findByHashedKey(hashKey).isPresent()) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Incorrect Hashed Key");
        if (!accountRepository.findById(ownId).get().getSecretKey().equals(secretKey)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Incorrect Secret Key");
        Account account = accountRepository.findById(ownId).get();
        if (transferType.equals("charge")){
            account.setBalance(account.getBalance().decreaseAmount(amount));
        } else if (transferType.equals("refund")) {
            account.setBalance(account.getBalance().increaseAmount(amount));
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"the transfer type is incorrect.");
        }
        return new Money(amount);
    }
}
