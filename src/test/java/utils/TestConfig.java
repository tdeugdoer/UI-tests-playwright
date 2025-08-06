package utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConfig {
    public final String BROWSER = System.getProperty("browser", "chromium");
    public final Integer BROWSER_WIDTH = Integer.parseInt(System.getProperty("browser.width", "1920"));
    public final Integer BROWSER_HEIGHT = Integer.parseInt(System.getProperty("browser.height", "1080"));
    public final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("headless", Boolean.FALSE.toString()));

    public final boolean ALLURE_SCREENSHOTS = Boolean.parseBoolean(System.getProperty("allure.screenshots", Boolean.TRUE.toString()));
    public final boolean ALLURE_PAGE_SOURCES = Boolean.parseBoolean(System.getProperty("allure.page.sources", Boolean.TRUE.toString()));

    public final long POLL_INTERVAL_MS = 500;
    public final long POLL_DELAY_MS = 500;

}