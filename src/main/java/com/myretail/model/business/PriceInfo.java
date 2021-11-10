package com.myretail.model.business;

/**
 * This class represents the object containing price and currency of the
 * product.
 *
 * @author Ashuthosh c 11-09-2021
 */
public class PriceInfo {
    private String currencyCode;
    private Float value;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public PriceInfo() {
        super();
    }

    public PriceInfo(String currencyCode, Float value) {
        super();
        this.currencyCode = currencyCode;
        this.value = value;
    }

}
