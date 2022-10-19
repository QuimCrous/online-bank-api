package com.bankonline.Final_Project.DTOs;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ThirdPartyDTO {
    @NotNull
    private Long ownId;
    @NotNull
    private Long otherId;
    @NotNull
    private BigDecimal amount;

    @NotNull
    private Integer secretKey;

    public ThirdPartyDTO(Long ownId, Long otherId, BigDecimal amount, Integer secretKey) {
        this.ownId = ownId;
        this.otherId = otherId;
        this.amount = amount;
        this.secretKey = secretKey;
    }

    public Long getOwnId() {
        return ownId;
    }

    public void setOwnId(Long ownId) {
        this.ownId = ownId;
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

    public Integer getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Integer secretKey) {
        this.secretKey = secretKey;
    }
}
