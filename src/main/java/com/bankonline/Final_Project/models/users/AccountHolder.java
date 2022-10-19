package com.bankonline.Final_Project.models.users;
import com.bankonline.Final_Project.embedables.Address;
import com.bankonline.Final_Project.models.accounts.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class AccountHolder extends User{
    private String mail;
    private String phone;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> primaryAccountList;

    @OneToMany(mappedBy = "secondaryOwner")
    @JsonIgnore
    private List<Account> secondaryAccountList;
    @Embedded
    private Address primaryAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name= "street", column = @Column(name = "mail_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mail_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mail_postal_code")),
            @AttributeOverride(name = "provinceState", column = @Column(name = "mail_province_state")),
            @AttributeOverride(name = "country", column = @Column(name = "mail_country"))
    })
    private Address mailingAddress;

    public AccountHolder() {
    }

    public AccountHolder(String name, String password, String mail, String phone, LocalDate birthDate, Address primaryAddress) {
        super(name, password);
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
    }

    public AccountHolder(String name, String password, String mail, String phone, LocalDate birthDate, List<Account> primaryAccountList, List<Account> secondaryAccountList, Address primaryAddress) {
        super(name, password);
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.primaryAccountList = primaryAccountList;
        this.secondaryAccountList = secondaryAccountList;
        this.primaryAddress = primaryAddress;
    }

    public AccountHolder(String name, String mail, String phone, LocalDate birthDate) {
        super(name);
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
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

    public List<Account> getPrimaryAccountList() {
        return primaryAccountList;
    }

    public void setPrimaryAccountList(List<Account> primaryAccountList) {
        this.primaryAccountList = primaryAccountList;
    }

    public List<Account> getSecondaryAccountList() {
        return secondaryAccountList;
    }

    public void setSecondaryAccountList(List<Account> secondaryAccountList) {
        this.secondaryAccountList = secondaryAccountList;
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
