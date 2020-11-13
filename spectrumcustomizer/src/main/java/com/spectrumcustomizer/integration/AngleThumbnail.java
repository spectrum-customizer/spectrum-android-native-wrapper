package com.spectrumcustomizer.integration;

public class AngleThumbnail {
    public String name;
    public String url;

    public AngleThumbnail() {}

    @Override
    public String toString() {
        return "{\n" +
                "  name='" + name + "\'," + '\n' +
                "  url='" + url + '\'' + '\n' +
                "}\n";
    }
}
