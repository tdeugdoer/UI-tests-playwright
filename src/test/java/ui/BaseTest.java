package ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import org.awaitility.Awaitility;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.BrowserFactory;
import utils.TestConfig;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public abstract class BaseTest {
    protected static final Playwright playwright = Playwright.create();

    static {
        Awaitility.setDefaultPollInterval(Duration.ofMillis(TestConfig.POLL_INTERVAL_MS));
        Awaitility.setDefaultPollDelay(Duration.ofMillis(TestConfig.POLL_DELAY_MS));
    }

    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass(alwaysRun = true)
    public void launchBrowser() {
        browser = BrowserFactory.createBrowser(playwright);
    }

    @BeforeMethod(alwaysRun = true)
    public void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(TestConfig.BROWSER_WIDTH, TestConfig.BROWSER_HEIGHT));
        page = context.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void closeContext(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshotOnFailure();
            capturePageSource();
        }
        context.close();
    }

    @AfterClass(alwaysRun = true)
    void closeBrowser() {
        browser.close();
    }

    @AfterSuite(alwaysRun = true)
    public void closePlaywright() {
        playwright.close();
    }

    private void captureScreenshotOnFailure() {
        Allure.addAttachment("Failed Test Screenshot", "image/png",
                new ByteArrayInputStream(page.screenshot()), ".png");
    }

    private void capturePageSource() {
        Allure.addAttachment("Page Source", "text/html",
                page.content(), ".html");

    }

}
