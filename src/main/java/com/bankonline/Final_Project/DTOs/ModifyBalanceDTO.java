package com.bankonline.Final_Project.DTOs;

import java.math.BigDecimal;

public class ModifyBalanceDTO {
    private Long accountId;
    private BigDecimal amount;
    private String type;

    public ModifyBalanceDTO(Long accountId, BigDecimal amount, String type) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
