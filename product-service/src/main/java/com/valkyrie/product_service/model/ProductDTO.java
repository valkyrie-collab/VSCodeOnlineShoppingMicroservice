package com.valkyrie.product_service.model;

import java.util.List;

public class ProductDTO {
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
    private List<Double> ratings;
    private List<String> customerIds;
    private double discountedPrice;
    private String shippingInformation;
    private List<ImageDTO> images;

    public String getId() {return id;}

    public String getName() {return name;}

    public String getDescription() {return description;}

    public String getCategory() {return category;}

    public String getBrand() {return brand;}

    public String getColor() {return color;}

    public String getVariant() {return variant;}

    public String getStatus() {return status;}

    public String getSearchKeyword() {return searchKeyword;}

    public String getShippingInformation() {return shippingInformation;}

    public int getPrice() {return price;}

    public int getQuantity() {return quantity;}

    public int getSize() {return size;}

    public int getDiscount() {return discount;}

    public List<Double> getRating() {return ratings;}

    public double getDiscountedPrice() {return discountedPrice;}

    public List<String> getCustomerId() {return customerIds;}

    public List<ImageDTO> getImages() {return images;}

    public ProductDTO setId(String id) {
        this.id = id;
        return this;
    }

    public ProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public ProductDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductDTO setCategory(String category) {
        this.category = category;
        return this;
    }

    public ProductDTO setPrice(int price) {
        this.price = price;
        return this;
    }

    public ProductDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductDTO setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductDTO setColor(String color) {
        this.color = color;
        return this;
    }

    public ProductDTO setSize(int size) {
        this.size = size;
        return this;
    }

    public ProductDTO setVariant(String variant) {
        this.variant = variant;
        return this;
    }

    public ProductDTO setDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public ProductDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public ProductDTO setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
        return this;
    }

    public ProductDTO setRating(List<Double> ratings) {
        this.ratings = ratings;
        return this;
    }

    public ProductDTO setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
        return this;
    }

    public ProductDTO setShippingInformation(String shippingInformation) {
        this.shippingInformation = shippingInformation;
        return this;
    }

    public ProductDTO setImages(List<ImageDTO> images) {
        this.images = images;
        return this;
    }

    public ProductDTO setCustomerId(List<String> customerIds) {
        this.customerIds = customerIds;
        return this;
    }

}
