package com.fooddelivery.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public class Address {
    
    @NotBlank(message = "Street is required")
    @Size(max = 200)
    private String street;
    
    @NotBlank(message = "City is required")
    @Size(max = 100)
    private String city;
    
    @NotBlank(message = "State is required")
    @Size(max = 100)
    private String state;
    
    @NotBlank(message = "Zip code is required")
    @Size(max = 20)
    private String zipcode;
    
    @NotBlank(message = "Country is required")
    @Size(max = 100)
    private String country;
    
    @NotBlank(message = "Phone is required")
    @Size(max = 20)
    private String phone;
    
    // Constructors
    public Address() {}
    
    public Address(String street, String city, String state, String zipcode, String country, String phone) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
        this.phone = phone;
    }
    
    // Getters and Setters
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
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
