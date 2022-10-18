package com.bankonline.Final_Project.Service.users;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AddressDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AccountHolderServiceInterface;
import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.accounts.CheckingAccount;
import com.bankonline.Final_Project.models.accounts.CreditCard;
import com.bankonline.Final_Project.models.accounts.SavingsAccount;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.accounts.CheckingAccountRepository;
import com.bankonline.Final_Project.repositories.accounts.CreditCardRepository;
import com.bankonline.Final_Project.repositories.accounts.SavingAccountRepository;
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
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    SavingAccountRepository savingAccountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    public String transferMoneyByAccountType(Long ownId, Long otherId, BigDecimal amount){

        if (savingAccountRepository.findById(ownId).isPresent()){

            return transferSavingAccount(ownId, otherId, amount);
        } else if (checkingAccountRepository.findById(ownId).isPresent()) {

            return transferCheckingAccount(ownId, otherId, amount);
        }else {

            return transferMoney(ownId, otherId,amount);
        }
    }
    public String transferSavingAccount(Long ownId, Long otherId, BigDecimal amount){
        SavingsAccount ownAccount = savingAccountRepository.findById(ownId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The owner account Id doesn't exist"));
        Account otherAccount = accountRepository.findById(otherId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The receptor account Id doesn't exist"));
        ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(amount)));
        otherAccount.setBalance((otherAccount.getBalance().increaseAmount(amount)));
        String response = ownAccount.getPrimaryOwner().getName() + " has transfer " + amount + " EUR to " + otherAccount.getPrimaryOwner().getName();
        if (ownAccount.getBalance().getAmount().compareTo(ownAccount.getMinimumBalance()) < 0){
            ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(ownAccount.getPenaltyFee())));
            response = response.concat("\nA penalty fee has been applied.");
        }
        accountRepository.saveAll(List.of(ownAccount, otherAccount));
        return response;
    }
    public String transferCheckingAccount(Long ownId, Long otherId, BigDecimal amount){
        CheckingAccount ownAccount = checkingAccountRepository.findById(ownId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The owner account Id doesn't exist"));
        Account otherAccount = accountRepository.findById(otherId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The receptor account Id doesn't exist"));
        ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(amount)));
        otherAccount.setBalance((otherAccount.getBalance().increaseAmount(amount)));
        String response = ownAccount.getPrimaryOwner().getName() + " has transfer " + amount + " EUR to " + otherAccount.getPrimaryOwner().getName();
        if (ownAccount.getBalance().getAmount().compareTo(ownAccount.getMinimumBalance().getAmount()) < 0){
            ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(ownAccount.getPenaltyFee())));
            response = response.concat("\nA penalty fee has been applied.");
        }
        accountRepository.saveAll(List.of(ownAccount, otherAccount));
        return response;
    }

    public String transferMoney(Long ownId, Long otherId, BigDecimal amount){
        Account ownAccount = accountRepository.findById(ownId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The owner account Id doesn't exist"));
        Account otherAccount = accountRepository.findById(otherId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The receptor account Id doesn't exist"));
        ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(amount)));
        otherAccount.setBalance((otherAccount.getBalance().increaseAmount(amount)));
        accountRepository.saveAll(List.of(ownAccount, otherAccount));
        return ownAccount.getPrimaryOwner().getName() + " has transfer " + amount + " EUR to " + otherAccount.getPrimaryOwner().getName();
    }

    public List<Account> getAccounts(Long accountHolderId){
        List<Account> accountList = accountRepository.findByPrimaryOwner(accountHolderRepository.findById(accountHolderId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The user doesn't exist.")));
        accountList.addAll(accountRepository.findBySecondaryOwner(accountHolderRepository.findById(accountHolderId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The user doesn't exist."))));
        return accountList;
    }

    public String getBalance(Long id){
        String response;
        if (savingAccountRepository.existsById(id)){
            response = getBalanceSavingAccount(id);
        } else if (checkingAccountRepository.existsById(id)) {
            response = getBalanceCheckingAccount(id);
        } else if (creditCardRepository.existsById(id)) {
            response = getBalanceCreditCard(id);
        } else {
            Account account = accountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The user doesn't exist."));
            response =  "The account with id: "+account.getId()+" has a balance of "+account.getBalance();
        }
        return response;
    }

    public String getBalanceSavingAccount(Long id){
        SavingsAccount account = savingAccountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The account doesn't exist."));
        String response = "";
        response = account.checkInterestRate(response);
        savingAccountRepository.save(account);
        return "The account with id: "+account.getId()+" has a balance of "+account.getBalance() + ". "+response;
    }

    public String getBalanceCheckingAccount(Long id){
        CheckingAccount account = checkingAccountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The account doesn't exist."));
        String response = "";
        response = account.checkMonthlyMaintenanceFee(response);
        checkingAccountRepository.save(account);

        return "The account with id: "+account.getId()+" has a balance of "+account.getBalance()+". "+response;
    }

    public String getBalanceCreditCard(Long id){
        CreditCard account = creditCardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The account doesn't exist."));
        String response = "";
        response = account.checkMonthlyInterestRate(response);
        System.out.println(response);
        creditCardRepository.save(account);
        return "The account with id: "+account.getId()+" has a balance of "+account.getBalance()+". " +response;
    }

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO){
        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(),accountHolderDTO.getMail(),accountHolderDTO.getPhone(),accountHolderDTO.getBirthDate());
        return accountHolderRepository.save(accountHolder);
    }

    public String addPrimaryAddress(Long id, AddressDTO addressDTO){
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        accountHolder.setPrimaryAddress(new Address(addressDTO.getStreet(),addressDTO.getCity(),addressDTO.getPostalCode(),addressDTO.getProvinceState(),addressDTO.getCountry()));
        accountHolderRepository.save(accountHolder);
        return "The primary address has been updated";
    }

    public String addMailingAddress(Long id, AddressDTO addressDTO){
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        accountHolder.setMailingAddress(new Address(addressDTO.getStreet(),addressDTO.getCity(),addressDTO.getPostalCode(),addressDTO.getProvinceState(),addressDTO.getCountry()));
        accountHolderRepository.save(accountHolder);
        return "The mailing address has been updated";
    }


}
