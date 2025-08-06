package pages.checkout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;

@Getter
public class CheckoutOrderInformationPage extends CheckoutBasePage {
    private final Locator paymentMethod;

    public CheckoutOrderInformationPage(Page page) {
        super(page);
        paymentMethod = page.locator("//li[@class='woocommerce-order-overview__payment-method method']/strong");
    }

}
