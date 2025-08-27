package com.valkyrie.seller_service.model;

import java.util.Date;
import java.util.List;

public class SellerDTO {
    private String id;
    private String name;
    private String email;
    private long phoneNumber;
    private String address;
    private String business;
    private String gstNumber;
    private Date registrationDate;
    private String bankAccountDetails;
    private String status;
    private int rating;
    private String description;
    private ImageDTO image;
    private DocumentDTO document;
    private List<ProductDTO> products;

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

    public ImageDTO getImage() {
        return image;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public SellerDTO setId(String id) {
        this.id = id;
        return this;
    }

    public SellerDTO setName(String name) {
        this.name = name;
        return this;
    }

    public SellerDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public SellerDTO setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public SellerDTO setBusiness(String business) {
        this.business = business;
        return this;
    }

    public SellerDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public SellerDTO setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
        return this;
    }

    public SellerDTO setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public SellerDTO setBankAccountDetails(String bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
        return this;
    }

    public SellerDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public SellerDTO setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public SellerDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public SellerDTO setImage(ImageDTO image) {
        this.image = image;
        return this;
    }

    public SellerDTO setDocument(DocumentDTO document) {
        this.document = document;
        return this;
    }

    public SellerDTO setProducts(List<ProductDTO> products) {
        this.products = products;
        return this;
    }
}
