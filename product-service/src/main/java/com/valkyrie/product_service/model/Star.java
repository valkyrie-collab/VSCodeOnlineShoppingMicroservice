package com.valkyrie.product_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "star")
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int oneStar;
    private int twoStar;
    private int threeStar;
    private int fourStar;
    private int fiveStar;
    private String customerId;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public int getId() {return id;}

    public int getOneStar() {return oneStar;}

    public int getTwoStar() {return twoStar;}

    public int getThreeStar() {return threeStar;}

    public int getFourStar() {return fourStar;}

    public int getFiveStar() {return fiveStar;}

    public String getCustomerId() {return customerId;}

    public Product getProduct() {return product;}

    public Star setId(int id) {
        this.id = id;
        return this;
    }

    public Star setOneStar(int oneStar) {
        this.oneStar = oneStar;
        return this;
    }

    public Star setTwoStar(int twoStar) {
        this.twoStar = twoStar;
        return this;
    }

    public Star setFourStar(int fourStar) {
        this.fourStar = fourStar;
        return this;
    }

    public Star setThreeStar(int threeStar) {
        this.threeStar = threeStar;
        return this;
    }

    public Star setFiveStar(int fiveStar) {
        this.fiveStar = fiveStar;
        return this;
    }

    public Star setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Star setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }
}
