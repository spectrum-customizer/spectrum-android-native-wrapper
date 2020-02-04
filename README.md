Spectrum Wrapper for Android
============================

Overview
--------

The Spectrum Customizer library provides a subclass of Android's fragment class with helper methods for interacting with Customizer Content. The customizer itself is loaded in a WebView.

Adding the library to a project
-------------------------------

Snippets for installing this plugin using maven, gradle or ivy can be found on [bintray](https://bintray.com/beta/#/spectrumcustomizer/SpectrumCustomizer/SpectrumCustomizer?tab=overview).


Update application permissions
------------------------------

Spectrum Customizers require internet and file system access. Appropriate permissions have been set in the Android manifest:

```xml

<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

```
Add a SpectrumView fragment to the layout
--------------------------------------

Add a SpectrumView Fragment:

```xml

<fragment android:name="com.spectrumcustomizer.integration.SpectrumView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/spectrum"
    />

```

In your activity you can cast the fragment to a SpectrumView:

```java

FragmentManager mFragmentManager = getSupportFragmentManager();

SpectrumView sv = (SpectrumView) mFragmentManager.findFragmentById(R.id.spectrum);

```

Once you have a reference to a SpectrumView instance you can initialize the customizer with either a SKU or a Spectrum recipe ID.

```java
// customizerUrl is a string url that points to the Spectrum Customizer Javascript.

sv.LoadRecipe(readableId, customizerUrl);

// or

sv.LoadSku(product1, customizerUrl);

```

Add callbacks to the implementing activity
------------------------------------------

An activity needs to provide an implementation of the SpectrumCallback interface to SpectrumView's `onEvent` method in order for a user to add an item to the cart or for the customizer to retrieve a product's price. Here is an example implementation:

```java

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

Example Implementation
----------------------

A simple example implementation (the app project) can be found in the repo.
