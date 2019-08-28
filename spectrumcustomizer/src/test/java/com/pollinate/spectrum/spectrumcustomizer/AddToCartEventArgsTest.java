package com.pollinate.spectrum.spectrumcustomizer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
