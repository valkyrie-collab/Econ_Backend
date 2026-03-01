package com.valkyrie.seller.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "seller")
public class Seller {
    @Id
    private String username;
    private String name;
    private String address;
    private long number;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public long getNumber() {
        return number;
    }

    public Seller setUsername(String username) {
        this.username = username;
        return this;
    }

    public Seller setName(String name) {
        this.name = name;
        return this;
    }
    
    public Seller setAddress(String address) {
        this.address = address;
        return this;
    }

    public Seller setNumber(long number) {
        this.number = number;
        return this;
    }
}
