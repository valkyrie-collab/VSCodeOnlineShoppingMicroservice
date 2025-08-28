package com.valkyrie.order_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String id;
    private String productId;
    private String customerId;
    private String productName;
    private String productDescription;
    private String shippingInformation;
    private int quantity;
    private int price;

    public String getId() {return id;}

    public String getProductId() {return productId;}

    public String getCustomerId() {return customerId;}

    public String getProductName() {return productName;}

    public String getProductDescription() {return productDescription;}

    public String getShippingInformation() {return shippingInformation;}

    public int getQuantity() {return quantity;}

    public int getPrice() {return price;}

    public Order setId(String id) {
        this.id = id;
        return this;
    }

    public Order setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public Order setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public Order setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Order setProductionDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public Order setShippingInformation(String shippingInformation) {
        this.shippingInformation = shippingInformation;
        return this;
    }

    public Order setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Order setPrice(int price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return id + productId + customerId + productDescription + productName + price;
    }
}
