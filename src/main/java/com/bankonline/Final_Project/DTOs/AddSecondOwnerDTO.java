package com.bankonline.Final_Project.DTOs;

public class AddSecondOwnerDTO {
    private Long accountId;
    private Long accountHolderId;

    public AddSecondOwnerDTO(Long accountId, Long accountHolderId) {
        this.accountId = accountId;
        this.accountHolderId = accountHolderId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Long accountHolderId) {
        this.accountHolderId = accountHolderId;
    }
}
