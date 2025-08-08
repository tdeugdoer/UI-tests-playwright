package ui.menu;

import component.header.HeaderComponent;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.cart.CartPage;
import pages.menu.MenuPage;
import ui.BaseTest;
import utils.FailMessages;
import utils.TestConstants;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuPageTest extends BaseTest {
    private MenuPage menuPage;
    private CartPage cartPage;
    private HeaderComponent header;

    @BeforeMethod
    public void setUp() {
        page.navigate(TestConstants.Urls.BASE_URL + TestConstants.Urls.MENU_URL);

        menuPage = new MenuPage(page);
        cartPage = new CartPage(page);
        header = new HeaderComponent(page);
    }

    @Test(description = "Применение сортировки пицц")
    public void sorting() {
        menuPage.selectPriceAscOption();

        List<Float> menuCardsPrices = menuPage.getMenuCardsPricesList();
        List<Float> sortedCardsPrices = menuCardsPrices.stream()
                .sorted()
                .toList();

        Assertions.assertThat(menuCardsPrices)
                .as(FailMessages.MENU_NOT_SORTED_BY_PRICE_ASC)
                .isEqualTo(sortedCardsPrices);
    }

    @Test(description = "Фильтрация пицц по цене")
    public void priceFiltering() {
        Integer expectedMinPrice = 300;
        Integer expectedMaxPrice = 480;

        menuPage.changeMinPrice(expectedMinPrice)
                .changeMaxPrice(expectedMaxPrice)
                .clickPriceFilteringButton();
        List<Float> menuCardsPrices = menuPage.getMenuCardsPricesList();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(menuCardsPrices)
                    .as(FailMessages.MENU_NOT_FILTERED_BY_MIN_PRICE)
                    .allMatch(price -> price >= expectedMinPrice);
            softly.assertThat(menuCardsPrices)
                    .as(FailMessages.MENU_NOT_FILTERED_BY_MAX_PRICE)
                    .allMatch(price -> price <= expectedMaxPrice);
        });
    }

    @Test(description = "Добавление пиццы в корзину")
    public void additionToCart() {
        menuPage.addToCartFirstProduct();
        header.clickLinkToCart();
        Boolean cartExist = cartPage.loadingProductTable();

        assertThat(cartExist)
                .as(FailMessages.ELEMENT_NOT_EXIST)
                .isFalse();
    }

}
