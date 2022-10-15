package com.bankonline.Final_Project.models.accounts;
import com.bankonline.Final_Project.embedables.Money;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.models.users.AccountHolder;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Money balance;

    @ManyToOne
    @JoinColumn(name = "primary_owner_user_id")
    private AccountHolder primaryOwner;
    @ManyToOne
    @JoinColumn(name = "secondary_owner_user_id")
    private AccountHolder secondaryOwner;

    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="currency", column = @Column(name = "fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "fee_amount"))
    })
    private final Money penaltyFee = new Money(BigDecimal.valueOf(40L));

    public Account() {
    }

//    public Account(AccountHolder primaryOwner, LocalDate creationDate, Status status) {
//        this.primaryOwner = primaryOwner;
//        this.creationDate = creationDate;
//        this.status = status;
//    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, Status status) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }


    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }


}