package com.valkyrie.product_service.model;

public class StarDTO {
    private int id;
    private int oneStar;
    private int twoStar;
    private int threeStar;
    private int fourStar;
    private int fiveStar;
    private String customerId;

    public int getId() {return id;}

    public int getOneStar() {return oneStar;}

    public int getTwoStar() {return twoStar;}

    public int getThreeStar() {return threeStar;}

    public int getFourStar() {return fourStar;}

    public int getFiveStar() {return fiveStar;}

    public String getCustomerId() {return customerId;}

    public StarDTO setId(int id) {
        this.id = id;
        return this;
    }

    public StarDTO setOneStar(int oneStar) {
        this.oneStar = oneStar;
        return this;
    }

    public StarDTO setTwoStar(int twoStar) {
        this.twoStar = twoStar;
        return this;
    }

    public StarDTO setFourStar(int fourStar) {
        this.fourStar = fourStar;
        return this;
    }

    public StarDTO setThreeStar(int threeStar) {
        this.threeStar = threeStar;
        return this;
    }

    public StarDTO setFiveStar(int fiveStar) {
        this.fiveStar = fiveStar;
        return this;
    }

    public StarDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public double getRating() {
        return (double)(5 * fiveStar + 4 * fourStar +
                3 * threeStar + 2 * twoStar + oneStar) /
                (fiveStar + fourStar + threeStar + twoStar + oneStar);
    }
}
