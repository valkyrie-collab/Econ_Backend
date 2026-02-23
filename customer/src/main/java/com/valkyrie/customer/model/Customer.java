package com.valkyrie.customer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
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

    public Customer setUsername(String username) {
        this.username = username;
        return this;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }
    
    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    public Customer setNumber(long number) {
        this.number = number;
        return this;
    }
}
