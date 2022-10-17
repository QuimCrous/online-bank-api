package com.bankonline.Final_Project.DTOs;




import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountHolderDTO {

    @NotEmpty
    private String name;
    @Email
    private String mail;
    @NotEmpty
    private String phone;
    @Past
    private LocalDate birthDate;
    @NotEmpty
    private String accountType;
    @NotNull
    private BigDecimal initialBalance;



    public AccountHolderDTO(String name, String mail, String phone, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate, String accountType, BigDecimal initialBalance) {
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


}
