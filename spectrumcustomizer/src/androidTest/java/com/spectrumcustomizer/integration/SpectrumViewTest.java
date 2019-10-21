package com.spectrumcustomizer.integration;

import androidx.test.espresso.web.webdriver.Locator;


import com.novoda.espresso.ViewTestRule;
import com.spectrumcustomizer.integration.SpectrumView;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static org.hamcrest.Matchers.containsString;

public class SpectrumViewTest {

    @Rule
    public ViewTestRule<SpectrumView> rule = new ViewTestRule<>(R.layout.spectrum_view);

    @Test
    public void itLoadsAPage() {
        onWebView()
                .withElement(findElement(Locator.TAG_NAME, "h2"))
                .check(webMatches(getText(), containsString("Customizer")));
    }

}
