package com.bankonline.Final_Project.DTOs;

import java.math.BigDecimal;

public class AccHolderTransferDTO {
    private Long ownId;
    private Long otherId;
    private BigDecimal amount;

    public AccHolderTransferDTO(Long ownId, Long otherId, BigDecimal amount) {
        this.ownId = ownId;
        this.otherId = otherId;
        this.amount = amount;
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
}
