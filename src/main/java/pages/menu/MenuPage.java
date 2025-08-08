package pages.menu;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import pages.BasePage;
import utils.Constants;
import utils.Keys;

import java.util.List;

public class MenuPage extends BasePage {
    private final Locator menuCardsPrices;
    private final Locator addToCartButtons;
    private final Locator minPriceSlider;
    private final Locator maxPriceSlider;
    private final Locator minPriceValue;
    private final Locator maxPriceValue;
    private final Locator priceFilteringButton;
    private final Locator orderSelect;
    private final Locator title;

    public MenuPage(Page page) {
        super(page);
        menuCardsPrices = page.locator("//div[@class='price-cart']/descendant::bdi");
        addToCartButtons = page.locator("//a[@class='button product_type_simple add_to_cart_button ajax_add_to_cart']");
        minPriceSlider = page.locator("//span[contains(@class, 'ui-slider-handle ui-state-default ui-corner-all')][1]");
        maxPriceSlider = page.locator("//span[contains(@class, 'ui-slider-handle ui-state-default ui-corner-all')][2]");
        minPriceValue = page.locator("//span[@class='from']");
        maxPriceValue = page.locator("//span[@class='to']");
        priceFilteringButton = page.locator("//div[@class='price_slider_amount']/button[@type='submit']");
        orderSelect = page.locator("//select[@name='orderby']");
        title = page.locator("//h1[@class='entry-title ak-container']");
    }

    public MenuPage clickPriceFilteringButton() {
        priceFilteringButton.click();
        return this;
    }

    public MenuPage selectPriceAscOption() {
        orderSelect.selectOption("price");
        return this;
    }

    public MenuPage addToCartFirstProduct() {
        List<Locator> buttons = addToCartButtons.all();
        if (!buttons.isEmpty()) {
            buttons.getFirst().click();
        } else throw new IllegalStateException(Constants.ExceptionMessage.UNABLE_ADD_TO_CART_ERROR);

        return this;
    }

    public MenuPage changeMinPrice(Integer newValue) {
        if (newValue < 0) {
            throw new IllegalStateException(Constants.ExceptionMessage.NEGATIVE_PRICE_ERROR);
        }

        if (newValue > parseIntPriceValue(minPriceValue.textContent())) {
            increasePriceValueToValue(minPriceSlider, minPriceValue, newValue);
        } else throw new IllegalStateException(Constants.ExceptionMessage.REDUCTION_MIN_PRICE_ERROR);

        return this;
    }

    public MenuPage changeMaxPrice(Integer newValue) {
        if (newValue < 0) {
            throw new IllegalStateException(Constants.ExceptionMessage.NEGATIVE_PRICE_ERROR);
        }

        if (newValue < parseIntPriceValue(maxPriceValue.textContent())) {
            reducePriceValueToValue(maxPriceSlider, maxPriceValue, newValue);
        } else throw new IllegalStateException(Constants.ExceptionMessage.INCREASE_MAX_PRICE_ERROR);

        return this;
    }

    public List<Float> getMenuCardsPricesList() {
        menuCardsPrices.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
        return menuCardsPrices.all().stream()
                .map(Locator::textContent)
                .map(this::parseFloatPriceValue)
                .toList();
    }

    public String getTitle() {
        return title.textContent();
    }

    private void increasePriceValueToValue(Locator slider, Locator priceValue, Integer newValue) {
        while (!parseIntPriceValue(priceValue.textContent()).equals(newValue)) {
            moveRightSlider(slider);
        }
    }

    private void reducePriceValueToValue(Locator slider, Locator priceValue, Integer newValue) {
        while (!parseIntPriceValue(priceValue.textContent()).equals(newValue)) {
            moveLeftSlider(slider);
        }
    }

    private void moveRightSlider(Locator slider) {
        try {
            slider.dragTo(slider);
        } catch (PlaywrightException e) {
            throw new IllegalStateException(Constants.ExceptionMessage.SLIDER_OUT_OF_BOUNDS_ERROR);
        }
    }

    private void moveLeftSlider(Locator slider) {
        try {
            slider.click();
            page.keyboard().press(Keys.ARROW_LEFT);
        } catch (PlaywrightException e) {
            throw new IllegalStateException(Constants.ExceptionMessage.SLIDER_OUT_OF_BOUNDS_ERROR);
        }
    }

}
