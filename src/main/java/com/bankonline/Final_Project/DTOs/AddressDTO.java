package com.bankonline.Final_Project.DTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class AddressDTO {
    @NotEmpty
    @NotBlank
    private String street;
    @NotEmpty
    @NotBlank
    private String city;
    @NotEmpty
    @NotBlank
    private String postalCode;
    @NotEmpty
    @NotBlank
    private String provinceState;
    @NotEmpty
    @NotBlank
    private String country;

    public AddressDTO(String street, String city, String postalCode, String provinceState, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.provinceState = provinceState;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvinceState() {
        return provinceState;
    }

    public void setProvinceState(String provinceState) {
        this.provinceState = provinceState;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
