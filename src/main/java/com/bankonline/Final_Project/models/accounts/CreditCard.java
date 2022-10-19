package com.bankonline.Final_Project.models.accounts;

import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.models.users.AccountHolder;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class CreditCard extends Account{

    @DecimalMax(value = "100000.00")
    @DecimalMin(value = "100.00")
    private BigDecimal creditLimit = BigDecimal.valueOf(100);

    @DecimalMin(value = "0.1")
    private BigDecimal interestRate = new BigDecimal(0.2);
    private LocalDate lastInterestDay;

    public CreditCard() {
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, Integer secretKey, BigDecimal creditLimit, BigDecimal interestRate, LocalDate lastInterestDay) {
        super(balance, primaryOwner, secondaryOwner, creationDate, secretKey);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.lastInterestDay = lastInterestDay;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
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

    public Money checkMonthlyInterestRate(){
        BigDecimal bigDecimal2 = interestRate.divide(BigDecimal.valueOf(12),4, RoundingMode.HALF_EVEN);
        while (Period.between(getLastInterestDay().plusMonths(1), LocalDate.now()).getMonths() >= 0){
            BigDecimal bigDecimal = getBalance().getAmount().multiply(bigDecimal2);
            setBalance((getBalance().decreaseAmount(bigDecimal)));
            setLastInterestDay(lastInterestDay.plusMonths(1));
        }
        setLastInterestDay(LocalDate.now());
        return getBalance();
    }


}
