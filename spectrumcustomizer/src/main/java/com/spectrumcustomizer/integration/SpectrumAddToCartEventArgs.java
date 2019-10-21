package com.spectrumcustomizer.integration;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class SpectrumAddToCartEventArgs {

    public String[] skus;
    public String recipeSetId;
    public Map<String,String> options;

    public SpectrumAddToCartEventArgs() {}
}
