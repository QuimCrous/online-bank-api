package com.bankonline.Final_Project.models.accounts;

import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.users.AccountHolder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class CreditCard extends Account{

    @Embedded
    private Money creditLimit = new Money(BigDecimal.valueOf(100));
    private BigDecimal interestRate = new BigDecimal(0.2);
    private LocalDate lastInterestDay;

    public CreditCard() {
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, Status status, Money penaltyFee, Money creditLimit, BigDecimal interestRate, LocalDate lastInterestDay) {
        super(balance, primaryOwner, secondaryOwner, creationDate, status);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.lastInterestDay = lastInterestDay;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastInterestDay() {
        return lastInterestDay;
    }

    public void setLastInterestDay(LocalDate lastInterestDay) {
        this.lastInterestDay = lastInterestDay;
    }
}
