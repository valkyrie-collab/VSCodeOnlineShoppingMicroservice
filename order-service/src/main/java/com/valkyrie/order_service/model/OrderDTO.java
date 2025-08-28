package com.valkyrie.order_service.model;

public class OrderDTO {
    private String id;
    private String customerId;
    private String productName;
    private String productDescription;
    private String shippingInformation;
    private int quantity;
    private int price;
    private ProductDTO product;

    public String getId() {return id;}

    public String getCustomerId() {return customerId;}

    public String getProductName() {return productName;}

    public String getProductDescription() {return productDescription;}

    public String getShippingInformation() {return shippingInformation;}

    public int getQuantity() {return quantity;}

    public int getPrice() {return price;}

    public ProductDTO getProduct() {return product;}

    public OrderDTO setId(String id) {
        this.id = id;
        return this;
    }

    public OrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderDTO setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public OrderDTO setProductDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public OrderDTO setShippingInformation(String shippingInformation) {
        this.shippingInformation = shippingInformation;
        return this;
    }

    public OrderDTO setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderDTO setPrice(int price) {
        this.price = price;
        return this;
    }

    public OrderDTO setProduct(ProductDTO product) {
        this.product = product;
        return this;
    }
}
