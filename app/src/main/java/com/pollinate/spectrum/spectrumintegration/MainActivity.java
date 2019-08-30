package com.pollinate.spectrum.spectrumintegration;

import androidx.appcompat.app.AppCompatActivity;

import com.pollinate.spectrum.spectrumcustomizer.SpectrumArguments;
import com.pollinate.spectrum.spectrumcustomizer.SpectrumView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button recipeButton = findViewById(R.id.load_recipe);

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
