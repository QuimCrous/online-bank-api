package com.bankonline.Final_Project.models.accounts;

import com.bankonline.Final_Project.embedables.Money;

import javax.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Money balance;


}
