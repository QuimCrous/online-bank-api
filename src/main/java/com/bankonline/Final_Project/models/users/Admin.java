package com.bankonline.Final_Project.models.users;

import javax.persistence.Entity;

@Entity
public class Admin extends User{

    public Admin() {
    }

    public Admin(String name, String password) {
        super(name, password);
    }

    public Admin(String name) {
        super(name);
    }
}
