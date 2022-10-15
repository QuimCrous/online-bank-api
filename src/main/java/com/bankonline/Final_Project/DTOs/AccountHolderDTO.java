package com.bankonline.Final_Project.DTOs;

import com.bankonline.Final_Project.embedables.Address;


import java.time.LocalDate;

public class AccountHolderDTO {
    private String name;
    private String phone;
    private String mail;
    private LocalDate birthDate;
    private Address primaryAddress;
    private Address mailingAddress;

    public AccountHolderDTO(String name, String phone, String mail, LocalDate birthDate, Address primaryAddress) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
    }

    public AccountHolderDTO(String name, String phone, String mail, LocalDate birthDate, Address primaryAddress, Address mailingAddress) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
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

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
}
