package com.valkyrie.order_service.model;

public class ProductDTO {
    private String name;
    private String description;
    private String brand;
    private String color;
    private int size;
    private String variant;
    private int discount;
    private Double rating;
    private ImageDTO image;

    public String getName() {return name;}

    public String getDescription() {return description;}

    public String getBrand() {return brand;}

    public String getColor() {return color;}

    public String getVariant() {return variant;}

    public int getSize() {return size;}

    public int getDiscount() {return discount;}

    public Double getRating() {return rating;}

    public ImageDTO getImage() {return image;}

    public ProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public ProductDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductDTO setBrand(String brand) {
        this.brand = brand;
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

    public ProductDTO setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public ProductDTO setImages(ImageDTO image) {
        this.image = image;
        return this;
    }

}
