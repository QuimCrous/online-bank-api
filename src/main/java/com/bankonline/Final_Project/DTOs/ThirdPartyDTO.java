package com.bankonline.Final_Project.DTOs;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ThirdPartyDTO {
    @NotNull
    private Long ownId;
    @NotNull
    private String transferType;
    @NotNull
    private BigDecimal amount;

    @NotNull
    private Integer secretKey;

    public ThirdPartyDTO(Long ownId, String transferType, BigDecimal amount, Integer secretKey) {
        this.ownId = ownId;
        this.transferType = transferType;
        this.amount = amount;
        this.secretKey = secretKey;
    }

    public Long getOwnId() {
        return ownId;
    }

    public void setOwnId(Long ownId) {
        this.ownId = ownId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(Long otherId) {
        this.transferType = transferType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Integer secretKey) {
        this.secretKey = secretKey;
    }
}
