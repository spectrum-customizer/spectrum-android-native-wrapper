package com.pollinate.spectrum.spectrumcustomizer;

import android.os.Parcel;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AddToCartEventArgsParcelableTest {

    SpectrumAddToCartEventArgs args = new SpectrumAddToCartEventArgs();
    String recipeSetReadableId = "ABCD1234";

    @Test
    public void implementsParcelable() {
        args.recipeSetReadableId = recipeSetReadableId;

        Parcel parcel = Parcel.obtain();
        args.writeToParcel(parcel, args.describeContents());
        parcel.setDataPosition(0);

        SpectrumAddToCartEventArgs fromParcel = SpectrumAddToCartEventArgs.CREATOR.createFromParcel(parcel);
        assertThat(fromParcel.recipeSetReadableId, is(recipeSetReadableId));
    }
}
