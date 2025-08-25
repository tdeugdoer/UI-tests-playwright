package pages.cart;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import pages.BasePage;
import utils.Keys;

import java.util.Objects;

public class CartPage extends BasePage {
    private final Locator productTable;
    private final Locator updateCartButton;
    private final Locator totalCartAmount;
    private final Locator proceedToPaymentButton;
    private final Locator couponCodeInput;
    private final Locator applyCouponButton;
    private final Locator removeButtons;
    private final Locator quantityInputs;
    private final Locator removeCouponButtons;

    public CartPage(Page page) {
        super(page);
        productTable = page.locator("//form[@class='woocommerce-cart-form']/table[contains(@class,'shop_table')]");
        updateCartButton = page.locator("//button[@name='update_cart']");
        totalCartAmount = page.locator("//tr[@class='order-total']//span[contains(@class,'amount')]");
        proceedToPaymentButton = page.locator("//a[contains(@class,'checkout-button')]");
        couponCodeInput = page.locator("//input[@id='coupon_code']");
        applyCouponButton = page.locator("//button[@name='apply_coupon']");
        removeButtons = page.locator("//a[@class='remove']");
        quantityInputs = page.locator("//div[@class='quantity']//input");
        removeCouponButtons = page.locator("//a[contains(@class,'remove-coupon')]");
    }

    public CartPage clickUpdateCartButton() {
        updateCartButton.click();
        return this;
    }

    public CartPage clickProceedToPaymentButton() {
        proceedToPaymentButton.click();
        return this;
    }

    public CartPage clickApplyCouponButton() {
        applyCouponButton.click();
        return this;
    }

    public Integer getFirstProductQuantity() {
        if (quantityInputs.count() > 0) {
            return Integer.valueOf(Objects.requireNonNull(quantityInputs.first().inputValue()));
        }
        return 0;
    }

    public CartPage enterFirstProductQuantity(int quantity) {
        quantityInputs.first().fill(String.valueOf(quantity));
        return this;
    }

    public Float getTotalCartAmount() {
        return parseFloatPriceValue(totalCartAmount.textContent());
    }

    public CartPage enterCouponCode(String couponCode) {
        couponCodeInput.fill(couponCode);
        couponCodeInput.press(Keys.ENTER);
        return this;
    }

    public CartPage tryRemoveCoupons() {
        try {
            removeCouponButtons.all().forEach(Locator::clear);
        } catch (PlaywrightException e) {
        }
        return this;
    }

    public Boolean loadingProductTable() {
        try {
            Awaitility.await("Waiting for the bucket to be displayed")
                    .until(() -> {
                        if (productTable.isVisible()) {
                            return true;
                        }
                        page.reload();
                        return false;
                    });
            return true;
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

    public CartPage clearCart() {
        removeButtons.all().forEach(button -> {
            if (button.isVisible()) {
                button.click();
            }
        });
        return this;
    }

}
