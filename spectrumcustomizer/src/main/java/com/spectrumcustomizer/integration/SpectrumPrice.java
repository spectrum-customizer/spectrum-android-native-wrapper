package com.spectrumcustomizer.integration;

public class SpectrumPrice {

    public Double price;
    public Boolean inStock;
    public String sku;

    public SpectrumPrice(String price, Boolean inStock) {
        String safePrice = price.replaceAll("[^0-9\\.]", "");
        this.sku = "";
        this.price = Double.parseDouble(safePrice);
        this.inStock = inStock;
    }

    public SpectrumPrice(String sku, Double price, Boolean inStock) {
        this.sku = sku;
        this.price = price;
        this.inStock = inStock;
    }
}
