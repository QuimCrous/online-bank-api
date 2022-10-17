package com.bankonline.Final_Project.models.accounts;

import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.models.users.AccountHolder;


import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class SavingsAccount extends Account{

    @DecimalMin(value = "100.00")
    private BigDecimal minimumBalance = BigDecimal.valueOf(1000L);
    @Digits(integer = 1,fraction = 4)
    @DecimalMax(value = "0.5000")
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);
    private LocalDate lastInterestRate;

    public SavingsAccount() {
    }

    public SavingsAccount(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, BigDecimal minimumBalance, BigDecimal interestRate, LocalDate lastInterestRate) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.lastInterestRate = lastInterestRate;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastInterestRate() {
        return lastInterestRate;
    }

    public void setLastInterestRate(LocalDate lastInterestRate) {
        this.lastInterestRate = lastInterestRate;
    }

    public String checkInterestRate(String response){
        if (LocalDate.now().isAfter(lastInterestRate.plusYears(1))){
            BigDecimal bigDecimal = getBalance().getAmount().multiply(interestRate);
            setBalance((getBalance().increaseAmount(bigDecimal)));
            setLastInterestRate(LocalDate.now());
            System.out.println(interestRate);
            return response.concat("An annual interest rate has been applied.");
        }
        return response;
    }
}
