package pages.account;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class AccountBasePage extends BasePage {
    private final Locator editAccountLink;
    private final Locator ordersAccountLink;

    public AccountBasePage(Page page) {
        super(page);
        editAccountLink = page.locator("//li[contains(@class, 'edit-account')]");
        ordersAccountLink = page.locator("//li[contains(@class, 'orders')]/a");
    }

    public AccountBasePage clickEditAccountLink() {
        editAccountLink.click();
        return this;
    }

    public AccountBasePage clickOrdersAccountLink() {
        ordersAccountLink.click();
        return this;
    }

}
