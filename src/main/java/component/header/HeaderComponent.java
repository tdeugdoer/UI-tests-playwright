package component.header;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import component.BaseComponent;

public class HeaderComponent extends BaseComponent {
    private final Locator menuPageButton = page.locator("//li[contains(@class, 'menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-has-children')]/a");
    private final Locator menuCategoryButtons = page.locator("//ul[@class='sub-menu']//a");
    private final Locator promoPageButton = page.locator("//ul[@id='menu-primary-menu']//a[text()='Акции']");
    private final Locator linkToCart = page.locator("//a[@class='cart-contents wcmenucart-contents']");

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

}
