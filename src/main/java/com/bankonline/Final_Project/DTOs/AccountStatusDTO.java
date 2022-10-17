package com.bankonline.Final_Project.DTOs;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountStatusDTO {
    @NotNull
    private Long id;
    @NotEmpty
    private String status;

    public AccountStatusDTO(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
