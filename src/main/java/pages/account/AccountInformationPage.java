package pages.account;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccountInformationPage extends AccountBasePage {
    private final Locator message;

    public AccountInformationPage(Page page) {
        super(page);
        message = page.locator("//div[@class='woocommerce-message']");
    }

    public String getMessage() {
        return message.textContent();
    }

}
