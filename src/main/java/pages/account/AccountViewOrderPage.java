package pages.account;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccountViewOrderPage extends AccountBasePage {
    private final Locator customerDetails;

    public AccountViewOrderPage(Page page) {
        super(page);
        customerDetails = page.locator("//section[@class='woocommerce-customer-details']/address");
    }

    public String getCustomerDetails() {
        return customerDetails.textContent();
    }

}
