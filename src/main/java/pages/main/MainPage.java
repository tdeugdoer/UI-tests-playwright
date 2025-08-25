package pages.main;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.awaitility.Awaitility;
import pages.BasePage;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MainPage extends BasePage {
    private final Locator pizzaSliderUl;
    private final Locator prevPizzaSlick;
    private final Locator nextPizzaSlick;
    private final Locator pizzaSliderLi;
    private final Locator drinkImages;
    private final Locator dessertPageLinks;

    public MainPage(Page page) {
        super(page);
        pizzaSliderUl = page.locator("//h2[@class='prod-title' and contains(text(), 'Пицца')]/ancestor::aside/ul[@class='new-prod-slide remove-overload slick-initialized slick-slider']");
        prevPizzaSlick = page.locator("//h2[@class='prod-title' and contains(text(), 'Пицца')]/ancestor::aside/ul[@class='new-prod-slide remove-overload slick-initialized slick-slider']//a[@class='slick-prev']");
        nextPizzaSlick = page.locator("//h2[@class='prod-title' and contains(text(), 'Пицца')]/ancestor::aside/ul[@class='new-prod-slide remove-overload slick-initialized slick-slider']//a[@class='slick-next']");
        pizzaSliderLi = page.locator("//h2[@class='prod-title' and contains(text(), 'Пицца')]/ancestor::aside/ul[@class='new-prod-slide remove-overload slick-initialized slick-slider']//div[@class='slick-track']/li");
        drinkImages = page.locator("//h2[@class='prod-title' and contains(text(), 'Напитки')]/ancestor::aside//img[@class='attachment-shop_catalog size-shop_catalog wp-post-image']");
        dessertPageLinks = page.locator("//h2[@class='prod-title' and contains(text(), 'Десерты')]/ancestor::aside//img[@class='attachment-shop_catalog size-shop_catalog wp-post-image']/parent::a");
    }

    public MainPage slideLeftPizzaSlider(Integer times) {
        IntStream.range(0, times).forEach(i -> {
            List<Locator> initialItems = getVisiblePizzaInSlider();
            prevPizzaSlick.hover();
            prevPizzaSlick.click();
            waitingChangedVisibleItems(initialItems);
        });
        return this;
    }

    public MainPage slideRightPizzaSlider(Integer times) {
        IntStream.range(0, times).forEach(i -> {
            List<Locator> initialItems = getVisiblePizzaInSlider();
            nextPizzaSlick.hover();
            nextPizzaSlick.click();
            waitingChangedVisibleItems(initialItems);
        });
        return this;
    }

    public MainPage slidePizzaSlider(Integer times, String key) {
        pizzaSliderUl.click();
        IntStream.range(0, times).forEach(i -> {
            List<Locator> initialItems = getVisiblePizzaInSlider();
            page.keyboard().press(key);
            waitingChangedVisibleItems(initialItems);
        });
        return this;
    }

    public List<Locator> getVisiblePizzaInSlider() {
        return pizzaSliderLi.all().stream()
                .filter(el -> Objects.equals(el.getAttribute("aria-hidden"), "false"))
                .toList();
    }

    public long getCountDrinkWithVisibleCartButtons() {
        return drinkImages.all().stream()
                .filter(Locator::isVisible)
                .count();
    }

    public MainPage moveToFirstDrinkImage() {
        drinkImages.first().hover();
        return this;
    }

    public String getFirstDesertTitle() {
        return dessertPageLinks.first().getAttribute("title");
    }

    public void redirectToFirstDesert() {
        dessertPageLinks.first().click();
    }

    private void waitingChangedVisibleItems(List<Locator> initialItems) {
        Awaitility.await("Waiting for the displayed elements to change in the slider")
                .until(() -> !getVisiblePizzaInSlider().equals(initialItems));
    }

}
