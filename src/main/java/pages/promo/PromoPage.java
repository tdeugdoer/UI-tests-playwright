package pages.promo;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import pages.BasePage;

@Getter
public class PromoPage extends BasePage {
    private final Locator firstCoupon;

    public PromoPage(Page page) {
        super(page);
        firstCoupon = page.locator("//div[@class='content-page']/ul/li[1]/strong");
    }

}
