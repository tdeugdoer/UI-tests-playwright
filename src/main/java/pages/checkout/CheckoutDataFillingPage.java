package pages.checkout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import formData.CheckoutFormData;

public class CheckoutDataFillingPage extends CheckoutBasePage {
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator countrySelect;
    private final Locator addressInput;
    private final Locator cityInput;
    private final Locator stateInput;
    private final Locator postcodeInput;
    private final Locator phoneInput;
    private final Locator orderDateInput;
    private final Locator paymentInCashOnDeliveryRadioButton;
    private final Locator termsCheckbox;
    private final Locator placeOrderButton;

    public CheckoutDataFillingPage(Page page) {
        super(page);
        firstNameInput = page.locator("//input[@id='billing_first_name']");
        lastNameInput = page.locator("//input[@id='billing_last_name']");
        countrySelect = page.locator("//select[@id='billing_country']");
        addressInput = page.locator("//input[@id='billing_address_1']");
        cityInput = page.locator("//input[@id='billing_city']");
        stateInput = page.locator("//input[@id='billing_state']");
        postcodeInput = page.locator("//input[@id='billing_postcode']");
        phoneInput = page.locator("//input[@id='billing_phone']");
        orderDateInput = page.locator("//input[@id='order_date']");
        paymentInCashOnDeliveryRadioButton = page.locator("//input[@id='payment_method_cod']");
        termsCheckbox = page.locator("//input[@id='terms']");
        placeOrderButton = page.locator("//button[@id='place_order']");
    }

    public CheckoutDataFillingPage clickTermsCheckbox() {
        termsCheckbox.click();
        return this;
    }

    public CheckoutDataFillingPage clickPlaceOrderButton() {
        placeOrderButton.click();
        return this;
    }

    public CheckoutDataFillingPage fillOutOrderDetails(CheckoutFormData formData) {
        firstNameInput.fill(formData.getFirstName());
        lastNameInput.fill(formData.getLastName());
        countrySelect.selectOption(formData.getCountry());
        addressInput.fill(formData.getAddress());
        cityInput.fill(formData.getCity());
        stateInput.fill(formData.getState());
        postcodeInput.fill(formData.getPostcode());
        phoneInput.fill(formData.getPhone());
        return this;
    }

    public CheckoutDataFillingPage fillOrderDate(String orderDate) {
        orderDateInput.fill(orderDate);
        return this;
    }

}
