package pages;

import com.microsoft.playwright.Page;

public abstract class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected Integer parseIntPriceValue(String priceValue) {
        return Integer.parseInt(priceValue.replace("₽", ""));
    }

    protected Float parseFloatPriceValue(String priceValue) {
        return Float.parseFloat(priceValue
                .replace(",", ".")
                .replace("₽", ""));
    }

}
