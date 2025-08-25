package pages.account;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccountOrdersPage extends AccountBasePage {
    private final Locator orderNumbers;

    public AccountOrdersPage(Page page) {
        super(page);
        orderNumbers = page.locator("//td[@class='woocommerce-orders-table__cell woocommerce-orders-table__cell-order-number']/a");
    }

    public AccountOrdersPage redirectToFirstOrder() {
        orderNumbers.first().click();
        return this;
    }

}
