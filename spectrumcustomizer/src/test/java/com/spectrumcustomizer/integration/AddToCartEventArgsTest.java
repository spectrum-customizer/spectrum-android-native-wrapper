package com.spectrumcustomizer.integration;

import com.spectrumcustomizer.integration.SpectrumAddToCartEventArgs;

import org.junit.Before;
import org.junit.Test;

public class AddToCartEventArgsTest {

    SpectrumAddToCartEventArgs args;
    String recipeSetReadableId = "ABCD1234";

    @Before
    public void setup() {
        args = new SpectrumAddToCartEventArgs();
    }

    @Test
    public void hasRecipeSetId() {
        args.recipeSetReadableId = recipeSetReadableId;
        assertEquals(recipeSetReadableId, args.recipeSetReadableId);
    }

}
