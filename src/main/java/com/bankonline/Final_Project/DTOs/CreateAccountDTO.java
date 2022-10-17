package com.bankonline.Final_Project.DTOs;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateAccountDTO {
    @NotNull
    private Long id;
    @NotEmpty
    private String accountType;
    @NotNull
    private BigDecimal initialBalance;

    public CreateAccountDTO(Long id, String accountType, BigDecimal initialBalance) {
        this.id = id;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
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
}
