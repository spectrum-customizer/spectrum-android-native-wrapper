package com.spectrumcustomizer.integration;

public class CartItem {
    public String sku;
    public String name;
    public int quantity;

    @Override
    public String toString() {
        return "{\n" +
                "  sku='" + sku + "\',\n" +
                "  name='" + name + "\',\n" +
                "  quantity=" + quantity + "\n"
                + "}\n";
    }
}
