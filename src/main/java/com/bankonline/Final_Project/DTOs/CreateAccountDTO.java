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
    private BigDecimal interestRate;

    public CreateAccountDTO(Long id, String accountType, BigDecimal initialBalance, BigDecimal minimumBalance, BigDecimal interestRate) {
        this.id = id;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
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
}
