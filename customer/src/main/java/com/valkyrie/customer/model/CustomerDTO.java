package com.valkyrie.customer.model;

public class CustomerDTO {
    private String username;
    private String name;
    private String address;
    private long number;
    private ImageDTO img;

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

    public ImageDTO getImage() {
        return img;
    }

    public CustomerDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public CustomerDTO setName(String name) {
        this.name = name;
        return this;
    }
    
    public CustomerDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public CustomerDTO setNumber(long number) {
        this.number = number;
        return this;
    }

    public CustomerDTO setImage(ImageDTO img) {
        this.img = img;
        return this;
    }
}
