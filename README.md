Spectrum Wrapper for Android
============================

Overview
--------

The Spectrum Customizer library provides a subclass of Android's fragment class with helper methods for interacting with Customizer Content. The customizer itself is loaded in a WebView.

Adding the library to a project
-------------------------------

This library can be installed using Jitpack.

First, add jitpack to your root build.gradle:

```
allprojects {
  maven { url 'https://jitpack.io' }
}
```

Then add the dependency:

```
dependencies {
  implementation 'com.github.spectrum-customizer:spectrum-android-native-wrapper:3.1.0'
}
```


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
// or load with product as well (implementation dependent)
sv.LoadRecipe(String readableId, String url, String product)

// or load by sku
sv.LoadSku(product1, customizerUrl);

```

Add callbacks to the implementing activity
------------------------------------------

An activity needs to provide an implementation of the SpectrumCallback interface to SpectrumView's `onEvent` method in order for a user to add an item to the cart or for the customizer to retrieve a product's price. Here is an example implementation:

```java

sv.onEvent(new SpectrumCallback() {
    @Override
    public void addToCart(SpectrumAddToCartPayload payload) {
        // Add to cart logic
    }

    @Override
    public ArrayList<SpectrumPrice> getPrice(String[] skus, Map<String, String> options) {
         ArrayList<SpectrumPrice> result = new ArrayList<SpectrumPrice>();
         for (int i = 0; i < skus.length; i++) {
             result.add(new SpectrumPrice(skus[i], randomPrice(), true));
         }
         return result;
    }
});

```

Here is the implementation of SpectrumAddToCartPayload and dependent classes:

```java

public class SpectrumAddToCartPayload {

    public String recipeSetId;
    public List<CartItem> items;
    public String primaryThumbnailAngle;
    public List<AngleThumbnail> thumbnailsByAngle;
    public Map<String, String> options;

    public SpectrumAddToCartPayload() {}
}

public class CartItem {
    public String sku;
    public String name;
    public int quantity;
}

public class AngleThumbnail {
    public String angleName;
    public String url;
}

```


SpectrumPrice is a simple data transfer object:

```java

public class SpectrumPrice {
    public Double price;
    public Boolean inStock;
    public String sku;

     public SpectrumPrice(String sku, Double price, Boolean inStock) {
        this.sku = sku;
        this.price = price;
        this.inStock = inStock;
    }
}

```

Example Implementation
----------------------

A simple example implementation (the app project) can be found in the repo.


Note on targeting Android API 21
--------------------------------

There is a known issue when trying to use a WebView with
androidx.appcompat:appcompat:1.1.0 that causes the application to
crash.

Switching to 'androidx.appcompat:appcompat:1.2.0-alpha02' fixes the issue.

[Link to bug tracker ](https://issuetracker.google.com/issues/141132133)

[Link to Stack Overflow](https://stackoverflow.com/questions/41025200/android-view-inflateexception-error-inflating-class-android-webkit-webview)

