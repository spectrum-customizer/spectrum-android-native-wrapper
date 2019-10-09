package com.pollinate.spectrum.spectrumcustomizer;

import java.util.Map;

public interface SpectrumAddToCart {
    void addToCart(String[] skus, String recipeSetId, Map<String,String> options);
}
