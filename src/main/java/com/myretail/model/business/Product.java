package com.myretail.model.business;

/**
 * This class represents the json data object containing productID,productName
 * and product price information.
 *
 * @author Ashuthosh c 11-09-2021
 */
public class Product {
    private Long id;
    private String name;
    private PriceInfo currentPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriceInfo getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(PriceInfo currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Product() {
        super();
    }

    public Product(Long id, String name, PriceInfo currentPrice) {
        super();
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
    }

}
