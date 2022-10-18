package com.bankonline.Final_Project.Service.users;

import com.bankonline.Final_Project.Service.users.interfaces.ThirdPartyUserServiceInterface;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.accounts.CheckingAccountRepository;
import com.bankonline.Final_Project.repositories.accounts.SavingAccountRepository;
import com.bankonline.Final_Project.repositories.users.AccountHolderRepository;
import com.bankonline.Final_Project.repositories.users.ThirdPartyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ThirdPartyUserService implements ThirdPartyUserServiceInterface {

    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    SavingAccountRepository savingAccountRepository;
    @Autowired
    AccountHolderService accountHolderService;

    public Money transferMoneyByAccountType(Long ownId, Long otherId, BigDecimal amount){
        if (savingAccountRepository.findById(ownId).isPresent()){
            return accountHolderService.transferSavingAccount(ownId, otherId, amount);
        } else if (checkingAccountRepository.findById(ownId).isPresent()) {
            return accountHolderService.transferCheckingAccount(ownId, otherId, amount);
        }else {
            return accountHolderService.transferMoney(ownId, otherId,amount);
        }
    }
}
