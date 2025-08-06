package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {
    public static Browser createBrowser(Playwright playwright) {
        BrowserType browserType = getBrowserType(playwright);
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(TestConfig.HEADLESS);

        return browserType.launch(options);
    }

    private static BrowserType getBrowserType(Playwright playwright) {
        return switch (TestConfig.BROWSER) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            case "chromium" -> playwright.chromium();
            default -> playwright.chromium();
        };
    }

}