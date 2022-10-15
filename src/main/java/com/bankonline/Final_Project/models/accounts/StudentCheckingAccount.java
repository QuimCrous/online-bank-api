package com.bankonline.Final_Project.models.accounts;

import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.users.AccountHolder;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class StudentCheckingAccount extends Account{
    public StudentCheckingAccount() {
    }

    public StudentCheckingAccount(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, Status status, Money penaltyFee) {
        super(balance, primaryOwner, secondaryOwner, creationDate, status, penaltyFee);
    }
}
