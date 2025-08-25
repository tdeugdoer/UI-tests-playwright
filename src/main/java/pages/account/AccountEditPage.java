package pages.account;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.nio.file.Path;

public class AccountEditPage extends AccountBasePage {
    private final Locator uploadFileInput;
    private final Locator saveAccountDetailsButton;

    public AccountEditPage(Page page) {
        super(page);
        uploadFileInput = page.locator("//input[@id='uploadFile']");
        saveAccountDetailsButton = page.locator("//button[@name='save_account_details']");
    }

    public AccountEditPage clickSaveAccountDetailsButton() {
        saveAccountDetailsButton.click();
        return this;
    }

    public AccountEditPage fillUploadFileInput(Path filePath) {
        uploadFileInput.setInputFiles(filePath);
        return this;
    }

}
