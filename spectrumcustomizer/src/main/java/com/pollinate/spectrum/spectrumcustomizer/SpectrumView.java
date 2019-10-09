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

public class SpectrumView extends WebView {

    private String customizerSource;
    private SpectrumAddToCart addToCartListener;


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
                if (addToCartListener != null) {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    SpectrumAddToCartEventArgs cartEventArgs = gson.fromJson(args, SpectrumAddToCartEventArgs.class);
                    addToCartListener.addToCart(cartEventArgs.skus, cartEventArgs.recipeSetId, cartEventArgs.options);
                }
            }
        }, "SpectrumNative");

    }

    public void LoadRecipe(SpectrumArguments args) {

        String serialized = serializeArguments(args);
        this.evaluateJavascript("integration.loadRecipe('" + serialized + "');", null);
    }

    public void LoadSku(SpectrumArguments args) {

        String serialized = serializeArguments(args);
        this.evaluateJavascript("integration.loadSku('" + serialized + "');", null);
    }

    public void onAddToCart(SpectrumAddToCart listener) {
        this.addToCartListener = listener;
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
