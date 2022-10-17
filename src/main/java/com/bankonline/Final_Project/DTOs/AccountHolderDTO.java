package com.bankonline.Final_Project.DTOs;




import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountHolderDTO {
    private Long id;
    private String name;
    private String mail;
    private String phone;
    private LocalDate birthDate;

    private String accountType;
    private BigDecimal initialBalance;

    public AccountHolderDTO(Long id, String accountType, BigDecimal initialBalance) {
        this.id = id;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
    }

    public AccountHolderDTO(String name, String mail, String phone, LocalDate birthDate, String accountType, BigDecimal initialBalance) {
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
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
