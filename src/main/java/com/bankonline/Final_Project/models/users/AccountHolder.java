package com.bankonline.Final_Project.models.users;

import javax.persistence.Entity;

@Entity
public class AccountHolder extends User{
    private String mail;
    private String phone;

    public AccountHolder() {
    }

    public AccountHolder(String name, String mail, String phone) {
        super(name);
        this.mail = mail;
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
