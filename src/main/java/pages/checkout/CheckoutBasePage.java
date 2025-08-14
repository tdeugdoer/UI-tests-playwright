package pages.checkout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class CheckoutBasePage extends BasePage {
    private final Locator postTitle;

    public CheckoutBasePage(Page page) {
        super(page);
        postTitle = page.locator("//h2[@class='post-title']");
    }

    public String getPostTitle() {
        return postTitle.textContent();
    }

}
