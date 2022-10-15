package com.bankonline.Final_Project.models.users;

import com.bankonline.Final_Project.embedables.Address;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class AccountHolder extends User{
    private String mail;
    private String phone;
    private LocalDate birthDate;
    @Embedded
    private Address primaryAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name= "street", column = @Column(name = "mail_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mail_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mail_postal_code")),
            @AttributeOverride(name = "provinceState", column = @Column(name = "mail_province_state")),
            @AttributeOverride(name = "country", column = @Column(name = "mail_country")),
    })
    private Address mailingAddress;

    public AccountHolder() {
    }

    public AccountHolder(String name, String mail, String phone, LocalDate birthDate, Address primaryAddress) {
        super(name);
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
    }

    public AccountHolder(String name, String mail, String phone, LocalDate birthDate, Address primaryAddress, Address mailingAddress) {
        super(name);
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
