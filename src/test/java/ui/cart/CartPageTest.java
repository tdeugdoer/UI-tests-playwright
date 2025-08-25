package ui.cart;

import component.header.HeaderComponent;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.auth.LoginPage;
import pages.cart.CartPage;
import pages.checkout.CheckoutBasePage;
import pages.menu.MenuPage;
import pages.promo.PromoPage;
import ui.BaseTest;
import utils.FailMessages;
import utils.TestConstants;
import utils.data.LoginData;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class CartPageTest extends BaseTest {
    private LoginPage loginPage;
    private CartPage cartPage;
    private MenuPage menuPage;
    private CheckoutBasePage checkoutBasePage;
    private PromoPage promoPage;
    private HeaderComponent header;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        loginPage = new LoginPage(page);
        cartPage = new CartPage(page);
        menuPage = new MenuPage(page);
        checkoutBasePage = new CheckoutBasePage(page);
        promoPage = new PromoPage(page);
        header = new HeaderComponent(page);

        step("Authentication", () -> {
            page.navigate(TestConstants.Urls.BASE_URL + TestConstants.Urls.MY_ACCOUNT_URL);
            loginPage.fillOutLoginForm(LoginData.EXISTING_EMAIL, LoginData.EXISTING_PASSWORD)
                    .clickLoginButton();
        });
        step("Navigate to cart", header::clickLinkToCart);
        step("Clear cart and go to menu page", () -> {
            cartPage.clearCart();
            header.clickMenuPageButton();
        });
        step("Add product to cart and navigate to cart", () -> {
            menuPage.addToCartFirstProduct();
            header.clickLinkToCart();
        });
        step("Remove existing coupons", () -> {
            cartPage.loadingProductTable();
            cartPage.tryRemoveCoupons();
        });
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        step("Clear cart", () -> {
            page.navigate(TestConstants.Urls.BASE_URL + TestConstants.Urls.CART_URL);
            cartPage.clearCart();
        });
    }

    @Test(description = "Increase and decrease product quantity")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Yahor Tserashkevich")
    @Link(name = "Website", url = "https://pizzeria.skillbox.cc/cart/")
    public void changeProductQuantity() {
        step("Increase quantity of first product in cart by one", () -> {
            Integer beforeQuantity = cartPage.getFirstProductQuantity();
            cartPage.enterFirstProductQuantity(beforeQuantity + 1);
            Integer afterQuantity = cartPage.getFirstProductQuantity();

            assertThat(afterQuantity)
                    .as(FailMessages.NUMBER_NOT_MATCH_EXPECTED)
                    .isEqualTo(beforeQuantity + 1);
        });
    }

    @Test(description = "Cart update after content modification")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Yahor Tserashkevich")
    @Link(name = "Website", url = "https://pizzeria.skillbox.cc/cart/")
    public void updateAfterChanges() {
        step("Update product quantity in cart", () -> {
            Integer beforeTotalCartAmount = cartPage.getFirstProductQuantity();
            Integer afterTotalCartAmount = cartPage.enterFirstProductQuantity(cartPage.getFirstProductQuantity() + 1)
                    .clickUpdateCartButton()
                    .getFirstProductQuantity();

            assertThat(afterTotalCartAmount)
                    .as(FailMessages.PRICE_SHOULD_INCREASE)
                    .isGreaterThan(beforeTotalCartAmount);
        });
    }

    @Test(description = "Proceed to checkout (authenticated user)")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Yahor Tserashkevich")
    @Link(name = "Website", url = "https://pizzeria.skillbox.cc/cart/")
    public void proceedToCheckoutForLoggedInUser() {
        step("Proceed to order payment", () -> {
            cartPage.clickProceedToPaymentButton();
            String message = checkoutBasePage.getPostTitle();

            assertThat(message)
                    .as(FailMessages.STRING_NOT_MATCH_EXPECTED)
                    .isEqualToIgnoringCase("Оформление заказа");
        });
    }

    @Test(description = "Apply promo code from 'Promotions' section")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yahor Tserashkevich")
    @Link(name = "Website", url = "https://pizzeria.skillbox.cc/cart/")
    public void applyPromoCodeFromSalesSection() {
        step("Get and apply promo code", () -> {
            header.clickPromoPageButton();

            String coupon = promoPage.getFistCoupon();
            header.clickLinkToCart();

            Float beforeTotalCartAmount = cartPage.getTotalCartAmount();
            Float afterTotalCartAmount = cartPage.enterCouponCode(coupon)
                    .clickApplyCouponButton()
                    .getTotalCartAmount();

            assertThat(afterTotalCartAmount)
                    .as(FailMessages.PRICE_SHOULD_DECREASE)
                    .isLessThan(beforeTotalCartAmount);
        });
    }

}