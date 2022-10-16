package com.bankonline.Final_Project;

import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.accounts.CheckingAccount;
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

		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setBalance(new Money(BigDecimal.valueOf(1000)));
		savingsAccount.setPrimaryOwner(accountHolder);
		savingsAccount.setCreationDate(LocalDate.now());
		savingsAccount.setStatus(Status.ACTIVE);
		savingsAccount.setLastInterestRate(LocalDate.now());
		accountRepository.save(savingsAccount);
		CheckingAccount checkingAccount = new CheckingAccount();
		checkingAccount.setBalance(new Money(BigDecimal.valueOf(1000)));
		checkingAccount.setPrimaryOwner(accountHolder1);
		checkingAccount.setCreationDate(LocalDate.now());
		checkingAccount.setStatus(Status.ACTIVE);
		checkingAccount.setLastInterestDay(LocalDate.now());
		accountRepository.save(checkingAccount);
	}
}
