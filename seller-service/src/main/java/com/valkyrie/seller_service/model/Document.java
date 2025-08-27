package com.valkyrie.seller_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "document")
public class Document {
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

    public int getId() {return id;}

    public String getName() {return name;}

    public String getType() {return type;}

    public byte[] getData() {return data;}

    public Document setId(int id) {
        this.id = id;
        return this;
    }

    public Document setName(String name) {
        this.name = name;
        return this;
    }

    public Document setType(String type) {
        this.type = type;
        return this;
    }

    public Document setData(byte[] data) {
        this.data = data;
        return this;
    }

    public Seller getSeller() {return seller;}

    public Document setSeller(Seller seller) {
        this.seller = seller;
        return this;
    }

    @Override
    public String toString() {
        return name + "." + type;
    }
}
