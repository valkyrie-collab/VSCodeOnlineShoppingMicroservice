package com.valkyrie.product_service.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private int price;
    private String brand;
    private int quantity;
    private String color;
    private int size;
    private String variant;
    private int discount;
    private String status;
    private String searchKeyword;
    private int oneStar;
    private int twoStar;
    private int threeStar;
    private int fourStar;
    private int fiveStar;
    private String shippingInformation;
//    @ElementCollection
    private String sellerId;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Image> images;

    public String getId() {return id;}

    public String getName() {return name;}

    public String getDescription() {return description;}

    public Product setId(String id) {
        this.id = id;
        return this;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Product setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Product setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Product setColor(String color) {
        this.color = color;
        return this;
    }

    public int getSize() {
        return size;
    }

    public Product setSize(int size) {
        this.size = size;
        return this;
    }

    public String getVariant() {
        return variant;
    }

    public Product setVariant(String variant) {
        this.variant = variant;
        return this;
    }

    public int getDiscount() {
        return discount;
    }

    public Product setDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Product setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public Product setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
        return this;
    }

    public int getOneStar() {
        return oneStar;
    }

    public Product setOneStar(int oneStar) {
        this.oneStar = oneStar;
        return this;
    }

    public int getTwoStar() {
        return twoStar;
    }

    public Product setTwoStar(int twoStar) {
        this.twoStar = twoStar;
        return this;
    }

    public int getThreeStar() {
        return threeStar;
    }

    public Product setThreeStar(int threeStar) {
        this.threeStar = threeStar;
        return this;
    }

    public int getFourStar() {
        return fourStar;
    }

    public Product setFourStar(int fourStar) {
        this.fourStar = fourStar;
        return this;
    }

    public int getFiveStar() {
        return fiveStar;
    }

    public Product setFiveStar(int fiveStar) {
        this.fiveStar = fiveStar;
        return this;
    }

    public String getShippingInformation() {
        return shippingInformation;
    }

    public Product setShippingInformation(String shippingInformation) {
        this.shippingInformation = shippingInformation;
        return this;
    }

    public String getSellerId() {
        return sellerId;
    }

    public Product setSellerId(String sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public Product setImages(List<Image> images) {
        this.images = images;
        return this;
    }

    @Override
    public String toString() {
        return id + description + price + brand + color + name + twoStar +
                category + sellerId + shippingInformation + oneStar + threeStar +
                searchKeyword + status + discount + variant + size + quantity + fourStar + fiveStar;
    }
}
