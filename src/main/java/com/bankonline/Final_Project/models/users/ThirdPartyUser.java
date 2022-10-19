package com.bankonline.Final_Project.models.users;

import javax.persistence.Entity;

@Entity
public class ThirdPartyUser extends User{

    private String hashedKey;

    public ThirdPartyUser() {
    }

    public ThirdPartyUser(String name, String hashedKey) {
        super(name);
        this.hashedKey = hashedKey;
    }

    public ThirdPartyUser(String name) {
        super(name);
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
