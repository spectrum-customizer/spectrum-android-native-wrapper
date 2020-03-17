package com.spectrumcustomizer.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.pollinate.spectrum.spectrumintegration.R;
import com.spectrumcustomizer.integration.SpectrumCallback;
import com.spectrumcustomizer.integration.SpectrumArguments;
import com.spectrumcustomizer.integration.SpectrumPrice;
import com.spectrumcustomizer.integration.SpectrumView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private SpectrumView sv;
    private static String customizerUrl = "https://madetoorderdev.blob.core.windows.net/spectrum-native-test/app.js";

    private String product1 = "example-product-1";
    private String product2 = "example-product-2";
    private String readableId = "BDTFLYW6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        sv = (SpectrumView) mFragmentManager.findFragmentById(R.id.spectrum);
        sv.LoadSku(product1, customizerUrl);

        sv.onEvent(new SpectrumCallback() {
            @Override
            public void addToCart(String[] skus, String recipeSetId, Map<String, String> options) {

                Context context = getApplicationContext();
                StringBuilder text = new StringBuilder();
                text.append("Recipe set ID: " + recipeSetId);
                text.append("\nSkus:\n");

                for (String sku : skus) {
                    text.append(sku + "\n");
                }

                for (Map.Entry<String, String> entry : options.entrySet()) {
                    text.append(entry.getKey() + ": " + entry.getValue() + "\n");
                }

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text.toString(), duration);

                toast.show();
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


        Button recipeButton = findViewById(R.id.load_recipe);

        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpectrumArguments args = new SpectrumArguments();
                MainActivity.this.sv.LoadRecipe(readableId, customizerUrl);
            }
        });

        Button skuOneButton = findViewById(R.id.load_sku_1);
        skuOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.sv.LoadSku(product1, customizerUrl);
            }
        });

        Button skuTwoButton = findViewById(R.id.load_sku_2);
        skuTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.sv.LoadSku(product2, customizerUrl);
            }
        });

    }

    private double randomPrice() {
        double digit = (double)(Math.random() * 80 + 10);
        return Math.round(digit * 100.0) / 100.0;
    }
}
