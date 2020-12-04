package com.spectrumcustomizer.integration;

public class AngleThumbnail {
    public String angleName;
    public String url;

    public AngleThumbnail() {}

    @Override
    public String toString() {
        return "{\n" +
                "  angleName='" + angleName + "\'," + '\n' +
                "  url='" + url + '\'' + '\n' +
                "}\n";
    }
}
