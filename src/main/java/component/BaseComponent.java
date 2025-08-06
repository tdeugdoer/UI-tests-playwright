package component;

import com.microsoft.playwright.Page;


public abstract class BaseComponent {
    protected Page page;

    public BaseComponent(Page page) {
        this.page = page;
    }

}
