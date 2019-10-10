package com.pollinate.spectrum.spectrumcustomizer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class SpectrumView extends WebView {

    private String customizerSource;
    private SpectrumCallback eventListeners;


    public SpectrumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SpectrumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        String url = "file:///android_asset/index.html";

        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);

        this.setWebContentsDebuggingEnabled(true);
        this.clearCache(true);
        this.loadUrl(url);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpectrumView);

        customizerSource = a.getString(R.styleable.SpectrumView_customizer_location);

        a.recycle();

        this.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                SpectrumView.this.SetCustomizerSource(SpectrumView.this.customizerSource);
            }
        });

        this.addJavascriptInterface(new CustomizerInterface() {

            @Override
            @JavascriptInterface
            public void addToCart(String args) {
                if (eventListeners != null) {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    SpectrumAddToCartEventArgs cartEventArgs = gson.fromJson(args, SpectrumAddToCartEventArgs.class);
                    eventListeners.addToCart(cartEventArgs.skus, cartEventArgs.recipeSetId, cartEventArgs.options);
                }
            }

            @Override
            @JavascriptInterface
            public void getPrice(String args) {
                if (eventListeners != null) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    final SpectrumGetPriceArgs getPriceArgs = gson.fromJson(args, SpectrumGetPriceArgs.class);
                    Map<String, SpectrumPrice> prices = eventListeners.getPrice(getPriceArgs.skus, getPriceArgs.options);
                    final String parsedPrices = gson.toJson(prices);

                    // JS communication has to be run on the UI thread so we wrap it in a Runnable
                    SpectrumView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            SpectrumView.this.evaluateJavascript("resolvePrice(" + Integer.toString(getPriceArgs.callbackId) + ", " + parsedPrices + ");", null);
                        }
                    });

                }
            }
        }, "SpectrumNative");

    }

    public void LoadRecipe(SpectrumArguments args) {

        String serialized = serializeArguments(args);
        this.evaluateJavascript("spectrum.loadRecipe('" + serialized + "');", null);
    }

    public void LoadSku(SpectrumArguments args) {

        String serialized = serializeArguments(args);
        this.evaluateJavascript("spectrum.loadSku('" + serialized + "');", null);
    }

    public void onEvent(SpectrumCallback listener) {
        this.eventListeners = listener;
    }

    private String serializeArguments(SpectrumArguments args) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(args);
    }

    private void SetCustomizerSource(String url) {
        this.evaluateJavascript("loadCustomizer('" + url + "');", null);
    }
}
