package component.header;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import component.BaseComponent;

public class HeaderComponent extends BaseComponent {
    private final Locator logoutButton = page.locator("//a[@class='logout']");
    private final Locator menuPageButton = page.locator("//li[contains(@class, 'menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-has-children')]/a");
    private final Locator menuCategoryButtons = page.locator("//ul[@class='sub-menu']//a");
    private final Locator accountPageButton = page.locator("//ul[@id='menu-primary-menu']//a[text()='Мой аккаунт']");
    private final Locator promoPageButton = page.locator("//ul[@id='menu-primary-menu']//a[text()='Акции']");
    private final Locator linkToCart = page.locator("//a[@class='cart-contents wcmenucart-contents']");

    public HeaderComponent(Page page) {
        super(page);
    }

    public HeaderComponent clickLinkToCart() {
        linkToCart.click();
        return this;
    }

    public void clickMenuCategoryButton(String category) {
        menuPageButton.hover();
        menuCategoryButtons.all().stream()
                .filter(button -> button.textContent().equalsIgnoreCase(category))
                .findFirst()
                .ifPresent(Locator::click);
    }

}
