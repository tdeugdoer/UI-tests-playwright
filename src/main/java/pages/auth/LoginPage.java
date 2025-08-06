package pages.auth;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class LoginPage extends BasePage {
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;

    public LoginPage(Page page) {
        super(page);
        usernameField = page.locator("//input[@id='username']");
        passwordField = page.locator("//input[@id='password']");
        loginButton = page.locator("//button[@name='login']");
    }

    public LoginPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    public LoginPage fillOutLoginForm(String username, String password) {
        usernameField.fill(username);
        passwordField.fill(password);
        return this;
    }

}
