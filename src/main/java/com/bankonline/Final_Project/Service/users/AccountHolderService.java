package com.bankonline.Final_Project.Service.users;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.AddressDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AccountHolderServiceInterface;
import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.accounts.CheckingAccount;
import com.bankonline.Final_Project.models.accounts.CreditCard;
import com.bankonline.Final_Project.models.accounts.SavingsAccount;
import com.bankonline.Final_Project.models.transactions.Transaction;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.models.users.Role;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.accounts.CheckingAccountRepository;
import com.bankonline.Final_Project.repositories.accounts.CreditCardRepository;
import com.bankonline.Final_Project.repositories.accounts.SavingAccountRepository;
import com.bankonline.Final_Project.repositories.transactions.TransactionRepository;
import com.bankonline.Final_Project.repositories.users.AccountHolderRepository;
import com.bankonline.Final_Project.repositories.users.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    public Money transferMoneyByAccountType(String name, Long ownId, Long otherId, BigDecimal amount){
        checkUserName(name, ownId);
        checkFraud(ownId);
        checkStrangeAmount(ownId, amount);
        if (savingAccountRepository.findById(ownId).isPresent()){
            return transferSavingAccount(ownId, otherId, amount);
        } else if (checkingAccountRepository.findById(ownId).isPresent()) {
            return transferCheckingAccount(ownId, otherId, amount);
        }else {
            return transferMoney(ownId, otherId,amount);
        }
    }
    public Money transferSavingAccount(Long ownId, Long otherId, BigDecimal amount){
        SavingsAccount ownAccount = savingAccountRepository.findById(ownId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The owner account Id doesn't exist"));
        if (ownAccount.getStatus().equals(Status.FROZEN)) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"The account is FROZEN");
        Account otherAccount = accountRepository.findById(otherId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The receptor account Id doesn't exist"));
        if (ownAccount.getBalance().getAmount().compareTo(amount) < 0) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Not enough money to do the transfer");
        ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(amount)));
        otherAccount.setBalance((otherAccount.getBalance().increaseAmount(amount)));
        if (ownAccount.getBalance().getAmount().compareTo(ownAccount.getMinimumBalance()) < 0){
            ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(ownAccount.getPenaltyFee())));
        }
        ownAccount.checkInterestRate();
        Transaction transaction = new Transaction(ownId,ownAccount,otherId,amount, LocalDateTime.now(),"savings");
        transactionRepository.save(transaction);
        accountRepository.saveAll(List.of(ownAccount, otherAccount));
        return ownAccount.getBalance();
    }
    public Money transferCheckingAccount(Long ownId, Long otherId, BigDecimal amount){
        CheckingAccount ownAccount = checkingAccountRepository.findById(ownId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The owner account Id doesn't exist"));
        if (ownAccount.getStatus().equals(Status.FROZEN)) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"The account is FROZEN");
        Account otherAccount = accountRepository.findById(otherId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The receptor account Id doesn't exist"));
        if (ownAccount.getBalance().getAmount().compareTo(amount) < 0) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Not enough money to do the transfer");
        ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(amount)));
        otherAccount.setBalance((otherAccount.getBalance().increaseAmount(amount)));
        if (ownAccount.getBalance().getAmount().compareTo(ownAccount.getMinimumBalance().getAmount()) < 0){
            ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(ownAccount.getPenaltyFee())));
        }
        ownAccount.checkMonthlyMaintenanceFee();
        Transaction transaction = new Transaction(ownId,ownAccount,otherId,amount, LocalDateTime.now(),"checking");
        transactionRepository.save(transaction);
        accountRepository.saveAll(List.of(ownAccount, otherAccount));
        return ownAccount.getBalance();
    }

    public Money transferMoney(Long ownId, Long otherId, BigDecimal amount){
        Account ownAccount = accountRepository.findById(ownId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The owner account Id doesn't exist"));
        if (ownAccount.getStatus().equals(Status.FROZEN)) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"The account is FROZEN");
        Account otherAccount = accountRepository.findById(otherId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The receptor account Id doesn't exist"));
        if (ownAccount.getBalance().getAmount().compareTo(amount) < 0) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Not enough money to do the transfer");
        if (ownAccount instanceof CreditCard) ((CreditCard) ownAccount).checkMonthlyInterestRate();
        ownAccount.setBalance((ownAccount.getBalance().decreaseAmount(amount)));
        otherAccount.setBalance((otherAccount.getBalance().increaseAmount(amount)));
        Transaction transaction = new Transaction(ownId,ownAccount,otherId,amount, LocalDateTime.now(),"transfer");
        transactionRepository.save(transaction);
        accountRepository.saveAll(List.of(ownAccount, otherAccount));
        return ownAccount.getBalance();
    }

    public List<Account> getAccounts(Long accountHolderId){
        List<Account> accountList = accountRepository.findByPrimaryOwner(accountHolderRepository.findById(accountHolderId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The user doesn't exist.")));
        accountList.addAll(accountRepository.findBySecondaryOwner(accountHolderRepository.findById(accountHolderId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The user doesn't exist."))));
        return accountList;
    }

    public Money getBalance(Long id, String userName){
        if (savingAccountRepository.existsById(id)){
            checkUserName(userName, id);
            return getBalanceSavingAccount(id);
        } else if (checkingAccountRepository.existsById(id)) {
            checkUserName(userName, id);
            return getBalanceCheckingAccount(id);
        } else if (creditCardRepository.existsById(id)) {
            checkUserName(userName, id);
            return getBalanceCreditCard(id);
        } else {
            Account account = accountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The account doesn't exist."));
            checkUserName(userName, id);
            return account.getBalance();
        }

    }

    public Money getBalanceSavingAccount(Long id){
        SavingsAccount account = savingAccountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The account doesn't exist."));
        account.checkInterestRate();
        savingAccountRepository.save(account);
        return account.getBalance();
    }

    public Money getBalanceCheckingAccount(Long id){
        CheckingAccount account = checkingAccountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The account doesn't exist."));
        account.checkMonthlyMaintenanceFee();
        checkingAccountRepository.save(account);
        return account.getBalance();
    }

    public Money getBalanceCreditCard(Long id){
        CreditCard account = creditCardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The account doesn't exist."));
        account.checkMonthlyInterestRate();
        creditCardRepository.save(account);
        return account.getBalance();
    }

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO){
        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(),passwordEncoder.encode(accountHolderDTO.getPassword()),accountHolderDTO.getMail(),accountHolderDTO.getPhone(),accountHolderDTO.getBirthDate());
        accountHolderRepository.save(accountHolder);
        roleRepository.save(new Role("USER", accountHolder));
        return accountHolder;
    }

    public Address addPrimaryAddress(Long id, AddressDTO addressDTO){
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        Address address = new Address(addressDTO.getStreet(),addressDTO.getCity(),addressDTO.getPostalCode(),addressDTO.getProvinceState(),addressDTO.getCountry());
        accountHolder.setPrimaryAddress(address);
        accountHolderRepository.save(accountHolder);
        return address;
    }

    public Address addMailingAddress(Long id, AddressDTO addressDTO){
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The id is incorrect."));
        Address address = new Address(addressDTO.getStreet(),addressDTO.getCity(),addressDTO.getPostalCode(),addressDTO.getProvinceState(),addressDTO.getCountry());
        accountHolder.setMailingAddress(address);
        accountHolderRepository.save(accountHolder);
        return address;
    }

    public void checkFraud(Long ownId) {
        if (transactionRepository.findByUserIdLastTransaction(ownId).isPresent()){
            if (transactionRepository.findByUserIdLastTransaction(ownId).get().until(LocalDateTime.now(), ChronoUnit.MILLIS) < 1000) {
                Account account = accountRepository.findById(ownId).get();
                account.setStatus(Status.FROZEN);
                accountRepository.save(account);
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Your account has been FROZEN due to fraud actions.");

            }
        }
    }

    public void checkUserName(String name, Long ownId){
        AccountHolder accountHolder = accountHolderRepository.findByName(name).get();
        if (!accountRepository.findById(ownId).get().getPrimaryOwner().equals(accountHolder)){
            if (accountRepository.findById(ownId).get().getSecondaryOwner() == null || !accountRepository.findById(ownId).get().getSecondaryOwner().equals(accountHolder)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not owner of the account.");
            }
        }
    }

    public void checkStrangeAmount(Long accountId, BigDecimal bigDecimal2){
        if (transactionRepository.maxAmountTransactionHistory(accountId).isPresent()){
            BigDecimal bigDecimal = transactionRepository.maxAmountTransactionHistory(accountId).get();
            if (bigDecimal.multiply(BigDecimal.valueOf(1.5)).compareTo(bigDecimal2) < 0){
                    Account account = accountRepository.findById(accountId).get();
                    account.setStatus(Status.FROZEN);
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Strange import amount, proceed to freeze the account");
            }
        }

    }


}
