Spectrum Wrapper for Android
============================

Overview
--------

The Spectrum Customizer library provides a subclass of Android's WebView with helper methods for interacting with Customizer Content.

Adding the library to a project
-------------------------------

Download the Spectrum Customizer jar from https://github.com/spectrum-customizer/spectrum-android-native-wrapper. In Android Studio ([documentation](https://developer.android.com/studio/projects/android-library)):

1. Click File > New > New Module
2. Click Import .JAR/.AAR Package and then click Next
3. Enter the location of the JAR file and click finish

Update application permissions
------------------------------

Spectrum Customizers require internet access. Set the appropriate permissions in the Android manifest:

```xml

<uses-permission android:name="android.permission.INTERNET"/>

```
Add a SpectrumView element to a layout
--------------------------------------

First, add the Spectrum namespace to the root element of the layout (`xmlns:spectrum="http://schemas.android.com/apk/res-auto"`). Then add a SpecrumViewElement:

```xml

 <com.spectrumcustomizer.integration.SpectrumView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/spectrum"
    spectrum:customizer_location="https://madetoorderdev.blob.core.windows.net/spectrum-native-test/app.js"
    />

```

The `customizer_location` property is the URL of the customizer.

Add callbacks to the implementing activity
------------------------------------------

An activity needs to provide an implementation of the SpectrumCallback interface to SpectrumView's `onEvent` method in order for a user to add an item to the cart or for the customizer to retrieve a product's price. Here is an example implementation:

```java

SpectrumView sv = findViewById(R.id.spectrum);

    sv.onEvent(new SpectrumCallback() {
        @Override
        public void addToCart(String[] skus, String recipeSetId, Map<String, String> options) {
            // Add to cart logic
        }

        @Override
        public Map<String, SpectrumPrice> getPrice(String[] skus, Map<String, String> options) {

            Map<String, SpectrumPrice> prices = new HashMap<>();

            prices.put("Sku1", new SpectrumPrice("$50.00", true));
            prices.put("Sku2", new SpectrumPrice("$100.00", false));

            return prices;
        }
    });

```

Prices are passed in as strings since they are only displayed in the UI, not used for any calculation. SpectrumPrice is a simple data transfer object:

```java

public class SpectrumPrice {

    public String price;
    public Boolean inStock;

    public SpectrumPrice(String price, Boolean inStock) {
        this.price = price;
        this.inStock = inStock;
    }
}

```

Loading a recipe or a product
-----------------------------

Existing recipes can be loaded by calling loadRecipe and products can be loaded by calling loadSku. Both expect a SpectrumArguments object:

```java

 // Load a recipe
 SpectrumView sv = findViewById(R.id.spectrum);
 SpectrumArguments args = new SpectrumArguments();
 args.recipeSetReadableId = "XFREHXNT";
 sv.LoadRecipe(args);

 // Load a product

 SpectrumView sv = findViewById(R.id.spectrum);
 SpectrumArguments args = new SpectrumArguments();
 args.productId = "example-pro-product-1";
 sv.LoadSku(args);

```

Example Implementation
----------------------

A simple example implementation (the app project) can be found in the repo.
