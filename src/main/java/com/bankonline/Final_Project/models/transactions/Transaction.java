package com.bankonline.Final_Project.models.transactions;

import com.bankonline.Final_Project.models.accounts.Account;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private Long otherId;

    private BigDecimal amount;

    private LocalDateTime time;

    private String transactionType;


    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Transaction() {
    }

    public Transaction(Long userId, Account account, Long otherId, BigDecimal amount, LocalDateTime time, String transactionType) {
        this.userId = userId;
        this.account = account;
        this.otherId = otherId;
        this.amount = amount;
        this.time = time;
        this.transactionType = transactionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /*private enum type;*/
}
