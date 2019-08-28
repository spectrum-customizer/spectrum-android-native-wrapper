package com.pollinate.spectrum.spectrumcustomizer;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

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

        String url = "https://madetoorderdev.blob.core.windows.net/spectrum-native-test/index.html";

        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        this.clearCache(true);
        this.loadUrl(url);

    }
}
