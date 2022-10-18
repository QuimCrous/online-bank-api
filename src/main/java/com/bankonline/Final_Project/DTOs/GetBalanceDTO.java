package com.bankonline.Final_Project.DTOs;

public class GetBalanceDTO {
    private Long userId;
    private Long accountId;

    public GetBalanceDTO(Long userId, Long accountId) {
        this.userId = userId;
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
