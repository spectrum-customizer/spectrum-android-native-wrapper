package com.pollinate.spectrum.spectrumcustomizer;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SpectrumView extends WebView {

    public SpectrumView(Context context) {
        super(context);
        init(context);
    }

    public SpectrumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpectrumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {

        String url = "file:///android_asset/index.html";

        this.setWebContentsDebuggingEnabled(true);

        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        this.clearCache(true);
        this.loadUrl(url);

    }

    public void LoadRecipe(SpectrumArguments args) {

        String serialized = serializeArguments(args);
        this.evaluateJavascript("integration.loadRecipe('" + serialized + "');", null);
    }

    public void LoadSku(SpectrumArguments args) {

        String serialized = serializeArguments(args);
        this.evaluateJavascript("integration.loadSku('" + serialized + "');", null);
    }

    private String serializeArguments(SpectrumArguments args) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(args);
    }
}
