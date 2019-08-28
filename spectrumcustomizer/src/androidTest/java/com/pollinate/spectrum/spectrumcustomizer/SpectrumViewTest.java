package com.pollinate.spectrum.spectrumcustomizer;

import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.novoda.espresso.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
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
