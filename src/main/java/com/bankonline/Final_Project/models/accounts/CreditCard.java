package com.bankonline.Final_Project.models.accounts;

import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.models.users.AccountHolder;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

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

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, BigDecimal creditLimit, BigDecimal interestRate, LocalDate lastInterestDay) {
        super(balance, primaryOwner, secondaryOwner, creationDate);
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

    public String checkMonthlyInterestRate(String response){
        int counter = 0;
        BigDecimal bigDecimal2 = interestRate.divide(BigDecimal.valueOf(12),4, RoundingMode.HALF_EVEN);
        while (LocalDate.now().compareTo(lastInterestDay.plusMonths(1)) >= 0){
            BigDecimal bigDecimal = getBalance().getAmount().multiply(bigDecimal2);
            setBalance((getBalance().increaseAmount(bigDecimal)));
            setLastInterestDay(lastInterestDay.plusMonths(1));
            counter++;
        }
        setLastInterestDay(LocalDate.now());
        if (counter==0){
            response = "";
        } else {
            response = response.concat("The interest rate for the last "+counter+" months has been added to the balance.");
        }
        return response;
    }


}
