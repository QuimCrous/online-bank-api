package com.bankonline.Final_Project.Service.users;
import com.bankonline.Final_Project.DTOs.AccountHolderDTO;
import com.bankonline.Final_Project.DTOs.CreateAccountDTO;
import com.bankonline.Final_Project.Service.users.interfaces.AdminServiceInterface;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.accounts.*;
import com.bankonline.Final_Project.models.users.*;
import com.bankonline.Final_Project.repositories.accounts.*;
import com.bankonline.Final_Project.repositories.users.AccountHolderRepository;
import com.bankonline.Final_Project.repositories.users.RoleRepository;
import com.bankonline.Final_Project.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

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
        if (!status.equals("FROZEN") && !status.equals("ACTIVE")) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Incorrect status.");
        Status status1 = Status.valueOf(status);
        account.setStatus(status1);
        return accountRepository.save(account);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteAccount(Long id){
        Account account = accountRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Incorrect Account id"));
        accountRepository.delete(account);
    }
    public void addSecondaryOwner(Long secondId, Long accountId){
        AccountHolder accountHolder2 = accountHolderRepository.findById(secondId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The Account Holder id is incorrect."));
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"The account id is incorrect."));
        account.setSecondaryOwner(accountHolder2);
        accountRepository.save(account);
    }
    public Account createNewAccount(AccountHolderDTO accountHolderDTO){
        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(),passwordEncoder.encode(accountHolderDTO.getPassword()), accountHolderDTO.getMail(),accountHolderDTO.getPhone(),accountHolderDTO.getBirthDate());
        userRepository.save(accountHolder);
        Role role = roleRepository.save(new Role("USER", accountHolder));
        return switch (accountHolderDTO.getAccountType()) {
            case "savingsaccount" ->
                    createSavingAccount(accountHolderDTO.getInitialBalance(), accountHolder, accountHolderDTO.getMinimumBalance(), accountHolderDTO.getInterestRate(), accountHolderDTO.getSecretKey());
            case "creditcard" ->
                    createCreditCard(accountHolder, accountHolderDTO.getMinimumBalance(), accountHolderDTO.getInterestRate(), accountHolderDTO.getSecretKey());
            case "checkingaccount" -> createCheckingAccount(accountHolderDTO.getInitialBalance(), accountHolder, accountHolderDTO.getSecretKey());
            default ->{
                roleRepository.delete(role);
                userRepository.delete(accountHolder);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Account Type");
            }
        };

    }

    public Account createNewAccountByUser(CreateAccountDTO createAccountDTO){
        AccountHolder accountHolder = accountHolderRepository.findById(createAccountDTO.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"The Account Holder id is incorrect."));
        return switch (createAccountDTO.getAccountType()) {
            case "savingsaccount" ->
                    createSavingAccount(createAccountDTO.getInitialBalance(), accountHolder, createAccountDTO.getMinimumBalance(), createAccountDTO.getInterestRate(), createAccountDTO.getSecretKey());
            case "creditcard" ->
                    createCreditCard(accountHolder, createAccountDTO.getMinimumBalance(), createAccountDTO.getInterestRate(), createAccountDTO.getSecretKey());
            case "checkingaccount" -> createCheckingAccount(createAccountDTO.getInitialBalance(), accountHolder, createAccountDTO.getSecretKey());
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Account Type");
        };

    }


    public Account createSavingAccount(BigDecimal initialBalance, AccountHolder accountHolder, BigDecimal minimumBalance, BigDecimal interestRate, Integer secretKey){
        SavingsAccount account = new SavingsAccount();
        account.setPrimaryOwner(accountHolder);
        account.setBalance(initialBalance);
        account.setMinimumBalance(minimumBalance);
        account.setInterestRate(interestRate);
        account.setSecretKey(secretKey);
        account.setCreationDate(LocalDate.now());
        account.setLastInterestRate(LocalDate.now());
        return savingAccountRepository.save(account);
    }
    public Account createCreditCard(AccountHolder accountHolder, BigDecimal minimumBalance, BigDecimal interestRate, Integer secretKey){
        CreditCard card = new CreditCard();
        card.setPrimaryOwner(accountHolder);
        card.setCreditLimit(minimumBalance);
        card.setBalance(minimumBalance);
        card.setInterestRate(interestRate);
        card.setSecretKey(secretKey);
        card.setCreationDate(LocalDate.now());
        card.setLastInterestDay(LocalDate.now());
        return creditCardRepository.save(card);
    }

    public Account createCheckingAccount(BigDecimal initialBalance, AccountHolder accountHolder, Integer secretKey){
        if (Period.between(accountHolder.getBirthDate(), LocalDate.now()).getYears() >= 24){
            CheckingAccount account1 = new CheckingAccount();
            account1.setPrimaryOwner(accountHolder);
            account1.setBalance(initialBalance);
            account1.setSecretKey(secretKey);
            account1.setCreationDate(LocalDate.now());
            account1.setLastInterestDay(LocalDate.now());
            return checkingAccountRepository.save(account1);
        } else {
            StudentCheckingAccount account1 = new StudentCheckingAccount();
            account1.setPrimaryOwner(accountHolder);
            account1.setCreationDate(LocalDate.now());
            account1.setBalance(initialBalance);
            account1.setSecretKey(secretKey);
            return studentCheckingAccountRepository.save(account1);
        }
    }
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public ThirdPartyUser createThirdPartyUser(String name, String hashedKey){
        ThirdPartyUser thirdPartyUser = new ThirdPartyUser(name, hashedKey);
        return userRepository.save(thirdPartyUser);
    }

    public AccountHolder addPassword(Long userId, String password){
        AccountHolder accountHolder = accountHolderRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"The Account Holder Id doesn't exist."));
        accountHolder.setPassword(passwordEncoder.encode("password"));
        return accountHolderRepository.save(accountHolder);
    }

    public Admin createAdmin(String name, String password){
        Admin admin = new Admin(name, passwordEncoder.encode(password));
        userRepository.save(admin);
        roleRepository.save(new Role("ADMIN", admin));
        return admin;
    }

    public Money getBalanceAccount(Long accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "The account Id doesn't exist."));
        return account.getBalance();
    }

}
