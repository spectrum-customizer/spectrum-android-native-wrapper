package com.spectrumcustomizer.integration;

import java.util.ArrayList;
import java.util.Map;

public interface SpectrumCallback {
    void addToCart(String[] skus, String recipeSetId, Map<String,String> options);
    ArrayList<SpectrumPrice> getPrice(String[] skus, Map<String, String> options);
}
