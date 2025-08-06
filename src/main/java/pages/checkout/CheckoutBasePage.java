package pages.checkout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import pages.BasePage;

@Getter
public class CheckoutBasePage extends BasePage {
    private final Locator postTitle;

    public CheckoutBasePage(Page page) {
        super(page);
        postTitle = page.locator("//h2[@class='post-title']");
    }

}
