package com.spectrumcustomizer.integration;

import java.util.ArrayList;
import java.util.Map;

public interface SpectrumCallback {
    void addToCart(SpectrumAddToCartPayload payload);
    ArrayList<SpectrumPrice> getPrice(String[] skus, Map<String, String> options);
}
