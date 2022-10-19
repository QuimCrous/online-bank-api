package com.bankonline.Final_Project.DTOs;




import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountHolderDTO {

    @NotEmpty
    @NotBlank
    private String name;
    @Email
    private String mail;
    @NotEmpty
    @NotBlank
    private String phone;
    @Past
    private LocalDate birthDate;
    @NotEmpty
    @NotBlank
    private String accountType;
    @NotNull
    private BigDecimal initialBalance;
    @NotNull
    private BigDecimal minimumBalance;
    @NotNull
    private BigDecimal interestRate;

    @NotEmpty
    @NotBlank
    private String password;


    public AccountHolderDTO(String name, String mail, String phone, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate, String accountType, BigDecimal initialBalance, BigDecimal minimumBalance, BigDecimal interestRate, String password) {
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
