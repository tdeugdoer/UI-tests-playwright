package ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.awaitility.Awaitility;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.BrowserFactory;
import utils.TestConfig;

import java.time.Duration;

public abstract class BaseTest {
    static {
        Awaitility.setDefaultPollInterval(Duration.ofMillis(TestConfig.POLL_INTERVAL_MS));
        Awaitility.setDefaultPollDelay(Duration.ofMillis(TestConfig.POLL_DELAY_MS));
    }

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeSuite
    public void launchPlaywright() {
        playwright = Playwright.create();
    }

    @BeforeClass
    public void launchBrowser() {
        browser = BrowserFactory.createBrowser(playwright);
    }

    @BeforeMethod
    public void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(TestConfig.BROWSER_WIDTH, TestConfig.BROWSER_HEIGHT));
        page = context.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void closeContext() {
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

}
