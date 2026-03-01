package com.valkyrie.seller.model;

public class SellerDTO {
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

    public SellerDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public SellerDTO setName(String name) {
        this.name = name;
        return this;
    }
    
    public SellerDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public SellerDTO setNumber(long number) {
        this.number = number;
        return this;
    }

    public SellerDTO setImage(ImageDTO img) {
        this.img = img;
        return this;
    }
}
