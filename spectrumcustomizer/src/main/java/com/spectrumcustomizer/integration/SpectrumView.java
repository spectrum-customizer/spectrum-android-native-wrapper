package com.spectrumcustomizer.integration;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pollinate.spectrum.spectrumcustomizer.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SpectrumView extends Fragment {

    private SpectrumCallback eventListeners;
    private String wrapperUrl = "file:///android_asset/index.html";

    private WebView mWebView;
    private String mCustomizerUrl = "";
    private String mActiveSku = "";
    private String mReadableId = "";

    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spectrum_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mWebView = view.findViewById(R.id.spectrum_view);

    }

    public void LoadRecipe(String readableId, String url) {

        mCustomizerUrl = url;
        mReadableId = readableId;
        mActiveSku = "";
        loadCustomizer();
    }

    public void LoadSku(String sku, String url) {
        mCustomizerUrl = url;
        mReadableId = "";
        mActiveSku = sku;
        loadCustomizer();
    }

    private void loadCustomizer() {

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                if (SpectrumView.this.mActiveSku != "") {
                    mWebView.evaluateJavascript("(function () { window.spectrumLoadProduct = '" + SpectrumView.this.mActiveSku + "' }())", null);
                }
                SpectrumView.this.SetCustomizerSource(SpectrumView.this.mCustomizerUrl);
            }
        });

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        mWebView.setWebContentsDebuggingEnabled(true);
        mWebView.clearCache(true);

        mWebView.addJavascriptInterface(new CustomizerInterface() {

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
                    SpectrumView.this.mWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            SpectrumView.this.mWebView.evaluateJavascript("resolvePrice(" + Integer.toString(getPriceArgs.callbackId) + ", " + parsedPrices + ");", null);
                        }
                    });

                }
            }
        }, "SpectrumNative");

        mWebView.setWebChromeClient(new WebChromeClient() {
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        Log.e(TAG, "Unable to create Image File", ex);
                    }

                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent[] intentArray;
                if(takePictureIntent != null) {
                    intentArray = new Intent[] {takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

                return true;
            }
        });

        String query = mReadableId == "" ? "" : "?recipeId=" + mReadableId;
        mWebView.loadUrl(wrapperUrl + query);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        Uri[] results = null;

        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                if (mCameraPhotoPath != null) {
                    results = new Uri[]{ Uri.parse(mCameraPhotoPath)};
                }
            } else {
                String dataString = data.getDataString();;
                if (dataString != null) {
                    results = new Uri[] { Uri.parse(dataString)};
                }
            }
        }
        mFilePathCallback.onReceiveValue(results);
        mFilePathCallback = null;
        return;
    }

    public void onEvent(SpectrumCallback listener) {
        this.eventListeners = listener;
    }

    private String serializeArguments(SpectrumArguments args) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(args);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);

        return imageFile;
    }

    private void SetCustomizerSource(String url) {
        mWebView.evaluateJavascript("loadCustomizer('" + url + "');", null);
    }

}
