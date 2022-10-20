package com.bankonline.Final_Project.DTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateAccountDTO {
    @NotNull
    private Long id;
    @NotEmpty
    @NotBlank
    private String accountType;
    @NotNull
    private BigDecimal initialBalance;
    @NotNull
    private BigDecimal minimumBalance;
    @NotNull
    @NotEmpty
    private BigDecimal interestRate;

    private Integer secretKey;

    public CreateAccountDTO(Long id, String accountType, BigDecimal initialBalance, BigDecimal minimumBalance, BigDecimal interestRate, Integer secretKey) {
        this.id = id;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.secretKey = secretKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
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

    public Integer getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Integer secretKey) {
        this.secretKey = secretKey;
    }
}
