package com.bankonline.Final_Project;

import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.accounts.CheckingAccount;
import com.bankonline.Final_Project.models.accounts.CreditCard;
import com.bankonline.Final_Project.models.accounts.SavingsAccount;
import com.bankonline.Final_Project.models.users.AccountHolder;
import com.bankonline.Final_Project.models.users.Admin;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class FinalProjectApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new Admin("Jaume"));
		AccountHolder accountHolder = userRepository.save(new AccountHolder("Quim","mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("patata","patata","patata","patata","patata")));
		AccountHolder accountHolder1 = userRepository.save(new AccountHolder("Oscar","mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("patata","patata","patata","patata","patata")));
		AccountHolder accountHolder2 = userRepository.save(new AccountHolder("Aña","mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("patata","patata","patata","patata","patata")));
		AccountHolder accountHolder3 = userRepository.save(new AccountHolder("Danny","mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("patata","patata","patata","patata","patata")));
		AccountHolder accountHolder4 = userRepository.save(new AccountHolder("Irina","mail@mail.com","999888777", LocalDate.of(1987,05,04), new Address("patata","patata","patata","patata","patata")));

		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setBalance(BigDecimal.valueOf(1000));
		savingsAccount.setPrimaryOwner(accountHolder);
		savingsAccount.setCreationDate(LocalDate.of(2021,01,01));
		savingsAccount.setLastInterestRate(LocalDate.of(2021,01,01));
		savingsAccount.setInterestRate(BigDecimal.valueOf(0.5000));
		accountRepository.save(savingsAccount);
		CheckingAccount checkingAccount = new CheckingAccount();
		checkingAccount.setBalance(BigDecimal.valueOf(1000));
		checkingAccount.setPrimaryOwner(accountHolder1);
		checkingAccount.setCreationDate(LocalDate.of(2020,01,01));
		checkingAccount.setLastInterestDay(LocalDate.of(2020,01,01));
		accountRepository.save(checkingAccount);
		SavingsAccount savingsAccount2 = new SavingsAccount();
		savingsAccount2.setBalance((BigDecimal.valueOf(1000)));
		savingsAccount2.setPrimaryOwner(accountHolder2);
		savingsAccount2.setCreationDate(LocalDate.now());
		savingsAccount2.setLastInterestRate(LocalDate.now());
		accountRepository.save(savingsAccount2);
		CheckingAccount checkingAccount2 = new CheckingAccount();
		checkingAccount2.setBalance((BigDecimal.valueOf(1000)));
		checkingAccount2.setPrimaryOwner(accountHolder3);
		checkingAccount2.setCreationDate(LocalDate.now());
		checkingAccount2.setLastInterestDay(LocalDate.now());
		accountRepository.save(checkingAccount2);
		CheckingAccount checkingAccount3 = new CheckingAccount();
		checkingAccount3.setBalance((BigDecimal.valueOf(1000)));
		checkingAccount3.setPrimaryOwner(accountHolder4);
		checkingAccount3.setCreationDate(LocalDate.now());
		checkingAccount3.setLastInterestDay(LocalDate.now());
		accountRepository.save(checkingAccount3);
		CreditCard creditCard = new CreditCard();
		creditCard.setPrimaryOwner(accountHolder4);
		creditCard.setCreationDate(LocalDate.of(2020,01,01));
		creditCard.setLastInterestDay(LocalDate.of(2020,01,01));
		creditCard.setCreditLimit(BigDecimal.valueOf(500L));
		creditCard.setInterestRate(BigDecimal.valueOf(0.15));
		creditCard.setBalance(creditCard.getCreditLimit());
		accountRepository.save(creditCard);
	}
}
