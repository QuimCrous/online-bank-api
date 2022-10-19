package com.bankonline.Final_Project;

import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.accounts.CheckingAccount;
import com.bankonline.Final_Project.models.accounts.CreditCard;
import com.bankonline.Final_Project.models.accounts.SavingsAccount;
import com.bankonline.Final_Project.models.transactions.Transaction;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.models.users.Admin;
import com.bankonline.Final_Project.models.users.Role;
import com.bankonline.Final_Project.models.users.ThirdPartyUser;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.transactions.TransactionRepository;
import com.bankonline.Final_Project.repositories.users.RoleRepository;
import com.bankonline.Final_Project.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class FinalProjectApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Admin admin = userRepository.save(new Admin("Cloud", passwordEncoder.encode("1234")));
		roleRepository.save(new Role("ADMIN", admin));
		AccountHolder accountHolder = userRepository.save(new AccountHolder("Tifa", passwordEncoder.encode("1234"), "mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("street one, 1","city1","10010","province1","country1")));
		roleRepository.save(new Role("USER", accountHolder));
		AccountHolder accountHolder1 = userRepository.save(new AccountHolder("Aeris",passwordEncoder.encode("1234"),"mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("street two, 2","city2","99999","province2","country2")));
		AccountHolder accountHolder2 = userRepository.save(new AccountHolder("Zelda",passwordEncoder.encode("1234"),"mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("street three, 3","city3","AP002","province3","country3")));
		AccountHolder accountHolder3 = userRepository.save(new AccountHolder("Link",passwordEncoder.encode("1234"),"mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("street four, 4","city4","08080","province4","country4")));
		AccountHolder accountHolder4 = userRepository.save(new AccountHolder("Ganondorf",passwordEncoder.encode("1234"),"mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("street five,5","city5","77898","province5","country5")));
		ThirdPartyUser thirdPartyUser = userRepository.save(new ThirdPartyUser("Colmado Ganondorf","aa@1"));

		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setBalance(BigDecimal.valueOf(1000));
		savingsAccount.setPrimaryOwner(accountHolder);
		savingsAccount.setCreationDate(LocalDate.of(2021,01,01));
		savingsAccount.setLastInterestRate(LocalDate.of(2021,01,01));
		savingsAccount.setInterestRate(BigDecimal.valueOf(0.5000));
		savingsAccount.setSecretKey(1234);
		accountRepository.save(savingsAccount);
		CheckingAccount checkingAccount = new CheckingAccount();
		checkingAccount.setBalance(BigDecimal.valueOf(1000));
		checkingAccount.setPrimaryOwner(accountHolder1);
		checkingAccount.setCreationDate(LocalDate.of(2020,01,01));
		checkingAccount.setLastInterestDay(LocalDate.of(2020,01,01));
		checkingAccount.setSecretKey(1234);
		accountRepository.save(checkingAccount);
		SavingsAccount savingsAccount2 = new SavingsAccount();
		savingsAccount2.setBalance((BigDecimal.valueOf(1000)));
		savingsAccount2.setPrimaryOwner(accountHolder2);
		savingsAccount2.setCreationDate(LocalDate.now());
		savingsAccount2.setLastInterestRate(LocalDate.now());
		savingsAccount2.setSecretKey(1234);
		accountRepository.save(savingsAccount2);
		CheckingAccount checkingAccount2 = new CheckingAccount();
		checkingAccount2.setBalance((BigDecimal.valueOf(80000)));
		checkingAccount2.setPrimaryOwner(accountHolder3);
		checkingAccount2.setCreationDate(LocalDate.now());
		checkingAccount2.setLastInterestDay(LocalDate.now());
		checkingAccount2.setSecretKey(1234);
		accountRepository.save(checkingAccount2);
		CheckingAccount checkingAccount3 = new CheckingAccount();
		checkingAccount3.setBalance((BigDecimal.valueOf(1000)));
		checkingAccount3.setPrimaryOwner(accountHolder4);
		checkingAccount3.setCreationDate(LocalDate.now());
		checkingAccount3.setLastInterestDay(LocalDate.now());
		checkingAccount3.setSecretKey(1234);
		accountRepository.save(checkingAccount3);
		CreditCard creditCard = new CreditCard();
		creditCard.setPrimaryOwner(accountHolder4);
		creditCard.setCreationDate(LocalDate.of(2020,01,01));
		creditCard.setLastInterestDay(LocalDate.of(2020,01,01));
		creditCard.setCreditLimit(BigDecimal.valueOf(500L));
		creditCard.setInterestRate(BigDecimal.valueOf(0.15));
		creditCard.setBalance(creditCard.getCreditLimit());
		creditCard.setSecretKey(1234);
		accountRepository.save(creditCard);
		CreditCard creditCard2 = new CreditCard();
		creditCard2.setPrimaryOwner(accountHolder4);
		creditCard2.setCreationDate(LocalDate.of(2020,01,01));
		creditCard2.setLastInterestDay(LocalDate.of(2020,01,01));
		creditCard2.setCreditLimit(BigDecimal.valueOf(500L));
		creditCard2.setInterestRate(BigDecimal.valueOf(0.15));
		creditCard2.setBalance(creditCard2.getCreditLimit());
		creditCard2.setStatus(Status.FROZEN);
		creditCard2.setSecretKey(1234);
		accountRepository.save(creditCard2);
		CheckingAccount checkingAccount4 = new CheckingAccount();
		checkingAccount4.setBalance((BigDecimal.valueOf(80000)));
		checkingAccount4.setPrimaryOwner(accountHolder3);
		checkingAccount4.setCreationDate(LocalDate.now());
		checkingAccount4.setLastInterestDay(LocalDate.now());
		checkingAccount4.setSecretKey(1234);
		accountRepository.save(checkingAccount4);

		Transaction transaction1 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(150L), LocalDateTime.now().minusDays(9),"test");
		Transaction transaction2 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(250L), LocalDateTime.now().minusDays(9),"test");
		Transaction transaction3 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(50L), LocalDateTime.now().minusDays(8),"test");
		Transaction transaction4 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(80L), LocalDateTime.now().minusDays(8),"test");
		Transaction transaction5 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(50L), LocalDateTime.now().minusDays(5),"test");
		Transaction transaction6 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(150L), LocalDateTime.now().minusDays(5),"test");
		Transaction transaction7 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(450L), LocalDateTime.now().minusDays(4),"test");
		Transaction transaction8 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(150L), LocalDateTime.now().minusDays(4),"test");
		Transaction transaction9 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(150L), LocalDateTime.now().minusDays(3),"test");
		Transaction transaction10 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(150L), LocalDateTime.now().minusDays(3),"test");
		Transaction transaction11 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(50L), LocalDateTime.now().minusDays(2),"test");
		Transaction transaction12 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(150L), LocalDateTime.now().minusDays(2),"test");
		Transaction transaction13 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(50L), LocalDateTime.now().minusDays(1),"test");
		Transaction transaction14 = new Transaction(accountHolder3.getUserId(),checkingAccount4, creditCard2.getId(), BigDecimal.valueOf(150L), LocalDateTime.now().minusDays(1),"test");
		transactionRepository.saveAll(List.of(transaction1,transaction2,transaction3,transaction4,transaction5,transaction6,transaction7,transaction8,transaction9,transaction10,transaction11,transaction12,transaction13,transaction14));

	}
}
