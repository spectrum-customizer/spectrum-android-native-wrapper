package com.pollinate.spectrum.spectrumintegration;

import androidx.appcompat.app.AppCompatActivity;

import com.pollinate.spectrum.spectrumcustomizer.SpectrumAddToCart;
import com.pollinate.spectrum.spectrumcustomizer.SpectrumArguments;
import com.pollinate.spectrum.spectrumcustomizer.SpectrumView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button recipeButton = findViewById(R.id.load_recipe);

        SpectrumView sv = findViewById(R.id.spectrum);

        sv.onAddToCart(new SpectrumAddToCart() {
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
        });

        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpectrumView sv = findViewById(R.id.spectrum);
                SpectrumArguments args = new SpectrumArguments();
                args.recipeSetReadableId = "XFREHXNT";
                sv.LoadRecipe(args);
            }
        });

        Button skuOneButton = findViewById(R.id.load_sku_1);
        skuOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpectrumView sv = findViewById(R.id.spectrum);
                SpectrumArguments args = new SpectrumArguments();
                args.productId = "tmx-pro-guess-originals";
                sv.LoadSku(args);
            }
        });

        Button skuTwoButton = findViewById(R.id.load_sku_2);
        skuTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpectrumView sv = findViewById(R.id.spectrum);
                SpectrumArguments args = new SpectrumArguments();
                args.productId = "tmx-pro-wilshire-38mm";
                sv.LoadSku(args);
            }
        });
    }
}
