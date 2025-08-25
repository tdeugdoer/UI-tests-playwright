package component.header;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import component.BaseComponent;

import java.util.List;

public class HeaderComponent extends BaseComponent {
    private final Locator menuPageButton = page.locator("//li[contains(@class, 'menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-has-children')]/a");
    private final Locator promoPageButton = page.locator("//ul[@id='menu-primary-menu']//a[text()='Акции']");
    private final Locator linkToCart = page.locator("//a[@class='cart-contents wcmenucart-contents']");
    private final Locator accountPageButton = page.locator("//ul[@id='menu-primary-menu']//a[text()='Мой аккаунт']");
    private final List<Locator> menuCategoryButtons = page.locator("//ul[@class='sub-menu']//a").all();

    public HeaderComponent(Page page) {
        super(page);
    }

    public HeaderComponent clickLinkToCart() {
        linkToCart.click();
        return this;
    }

    public HeaderComponent clickMenuPageButton() {
        menuPageButton.click();
        return this;
    }

    public HeaderComponent clickPromoPageButton() {
        promoPageButton.click();
        return this;
    }

    public HeaderComponent clickAccountPageButton() {
        accountPageButton.click();
        return this;
    }


}
