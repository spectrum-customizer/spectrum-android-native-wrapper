package com.spectrumcustomizer.integration;

import java.util.List;
import java.util.Map;

public class SpectrumAddToCartPayload {

    public String recipeSetId;
    public String sku;
    public Map<String, String> skusByName;
    public int quantity;
    public String primaryThumbnailAngle;
    public List<AngleThumbnail> thumbnailsByAngle;
    public Map<String, String> options;

    public SpectrumAddToCartPayload() {}
}
