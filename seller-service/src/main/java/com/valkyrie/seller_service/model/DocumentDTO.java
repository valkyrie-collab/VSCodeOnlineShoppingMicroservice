package com.valkyrie.seller_service.model;

import jakarta.persistence.Lob;

public class DocumentDTO {
    private int id;
    private String name;
    private String type;
    private byte[] data;

    public int getId() {return id;}

    public String getName() {return name;}

    public String getType() {return type;}

    public byte[] getData() {return data;}

    public DocumentDTO setId(int id) {
        this.id = id;
        return this;
    }

    public DocumentDTO setName(String name) {
        this.name = name;
        return this;
    }

    public DocumentDTO setType(String type) {
        this.type = type;
        return this;
    }

    public DocumentDTO setData(byte[] data) {
        this.data = data;
        return this;
    }
}
