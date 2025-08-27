package com.valkyrie.seller_service.model;

import jakarta.persistence.*;

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
    @OneToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    public int getId() {
        return id;
    }
    public Image setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }
    public Image setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }
    public Image setType(String type) {
        this.type = type;
        return this;
    }

    public byte[] getData() {
        return data;
    }
    public Image setData(byte[] data) {
        this.data = data;
        return this;
    }

    public Seller getSeller() {return seller;}

    public Image setSeller(Seller seller) {
        this.seller = seller;
        return this;
    }

    @Override
    public String toString() {return name + "." + type;}
}
