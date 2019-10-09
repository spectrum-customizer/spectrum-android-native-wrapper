package com.pollinate.spectrum.spectrumcustomizer;

import java.util.Map;

public interface SpectrumCallback {
    void addToCart(String[] skus, String recipeSetId, Map<String,String> options);
    Map<String, SpectrumPrice> getPrice(String[] skus, Map<String, String> options);
}
