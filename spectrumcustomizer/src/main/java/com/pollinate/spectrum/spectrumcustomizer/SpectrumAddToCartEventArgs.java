package com.pollinate.spectrum.spectrumcustomizer;

import android.os.Parcel;
import android.os.Parcelable;

public class SpectrumAddToCartEventArgs implements Parcelable {

    public String recipeSetReadableId;

    public SpectrumAddToCartEventArgs() {}

    public SpectrumAddToCartEventArgs(Parcel source) {
        this.recipeSetReadableId = source.readString();
    }

    // TODO: Images?

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeSetReadableId);
    }

    public static final Creator<SpectrumAddToCartEventArgs> CREATOR = new Creator<SpectrumAddToCartEventArgs>() {
        @Override
        public SpectrumAddToCartEventArgs createFromParcel(Parcel source) {
            return new SpectrumAddToCartEventArgs(source);
        }

        @Override
        public SpectrumAddToCartEventArgs[] newArray(int size) { return new SpectrumAddToCartEventArgs[size];}
    };

}
