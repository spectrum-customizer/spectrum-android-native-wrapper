package com.pollinate.spectrum.spectrumcustomizer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpectrumArgumentsTest {

    SpectrumArguments args;

    @Before
    public void setup() {
        args = new SpectrumArguments();
    }

    @Test
    public void existence() {
        assertNotNull(args);
    }

    @Test
    public void has_recipeSetReadabldIdProperty() {
        String readableId = "ABCD123";
        args.recipeSetReadableId = readableId;
        assertEquals(readableId, args.recipeSetReadableId);
    }

    @Test
    public void has_containerSelector() {
        String containerSelector = "#main";
        args.containerSelector = containerSelector;
        assertEquals(containerSelector, args.containerSelector);
    }

    @Test
    public void has_productId() {
        String productId = "pro-example-product";
        args.productId = productId;
        assertEquals(productId, args.productId);
    }
}