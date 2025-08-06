package component.footer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import component.BaseComponent;
import lombok.Getter;

@Getter
public class FooterComponent extends BaseComponent {
    private final Locator instagramButton = page.locator("//div[@class='banner-text wow fadeInLeft']//a[text()='Instagram']");

    public FooterComponent(Page page) {
        super(page);
    }

}
