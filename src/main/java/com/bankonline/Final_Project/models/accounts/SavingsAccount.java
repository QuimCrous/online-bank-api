package com.bankonline.Final_Project.models.accounts;

import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.users.AccountHolder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class SavingsAccount extends Account{

    @Embedded
    private Money minimumBalance = new Money(BigDecimal.valueOf(1000L));
    private BigDecimal interestRate = new BigDecimal(0.0025);
    private LocalDate lastInterestRate;

    public SavingsAccount() {
    }

    public SavingsAccount(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, Status status, Money penaltyFee, Money minimumBalance, BigDecimal interestRate, LocalDate lastInterestRate) {
        super(balance, primaryOwner, secondaryOwner, creationDate, status, penaltyFee);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.lastInterestRate = lastInterestRate;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
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
}
