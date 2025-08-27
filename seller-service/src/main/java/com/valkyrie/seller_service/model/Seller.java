package com.valkyrie.seller_service.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "seller")
public class Seller {
    @Id
    private String id;
    private String name;
    private String email;
    private long phoneNumber;
    private String address;
    private String business;
    private String gstNumber;
    private Date registrationDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    private String bankAccountDetails;
    private String status;
    private int rating;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getBusiness() {
        return business;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public String getBankAccountDetails() {
        return bankAccountDetails;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public Image getImage() {
        return image;
    }

    public Document getDocument() {
        return document;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Seller setId(String id) {
        this.id = id;
        return this;
    }

    public Seller setEmail(String email) {
        this.email = email;
        return this;
    }

    public Seller setName(String name) {
        this.name = name;
        return this;
    }

    public Seller setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Seller setBusiness(String business) {
        this.business = business;
        return this;
    }

    public Seller setAddress(String address) {
        this.address = address;
        return this;
    }

    public Seller setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
        return this;
    }

    public Seller setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public Seller setImage(Image image) {
        this.image = image;
        return this;
    }

    public Seller setBankAccountDetails(String bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
        return this;
    }

    public Seller setStatus(String status) {
        this.status = status;
        return this;
    }

    public Seller setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public Seller setDocument(Document document) {
        this.document = document;
        return this;
    }

    public Seller setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return id + name + email + phoneNumber + address +
                business + gstNumber + registrationDate + bankAccountDetails +
                status + rating + description;
    }
}
