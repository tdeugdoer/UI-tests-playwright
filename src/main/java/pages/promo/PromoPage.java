package pages.promo;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class PromoPage extends BasePage {
    private final Locator firstCoupon;

    public PromoPage(Page page) {
        super(page);
        firstCoupon = page.locator("//div[@class='content-page']/ul/li[1]/strong");
    }

    public String getFistCoupon() {
        return firstCoupon.textContent();
    }

}
