package com.bankonline.Final_Project.models.users;

import javax.persistence.Entity;

@Entity
public class ThirdPartyUser extends User{

    public ThirdPartyUser() {
    }

    public ThirdPartyUser(String name) {
        super(name);
    }
}
