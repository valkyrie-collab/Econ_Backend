package com.valkyrie.customer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    @Lob
    private byte[] data;
    private String username;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public byte[] getData() {
        return data;
    }

    public String getUsername() {
        return username;
    }

    public Image setName(String name) {
        this.name = name;
        return this;
    } 

    public Image setType(String type) {
        this.type = type;
        return this;
    }

    public Image setData(byte[] data) {
        this.data = data;
        return this;
    }

    public Image setUsername(String username) {
        this.username = username;
        return this;
    }
}
