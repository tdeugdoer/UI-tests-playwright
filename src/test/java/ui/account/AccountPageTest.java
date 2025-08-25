package ui.account;

import component.header.HeaderComponent;
import formData.CheckoutFormData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.account.AccountBasePage;
import pages.account.AccountEditPage;
import pages.account.AccountInformationPage;
import pages.account.AccountOrdersPage;
import pages.account.AccountViewOrderPage;
import pages.auth.LoginPage;
import pages.cart.CartPage;
import pages.checkout.CheckoutDataFillingPage;
import pages.menu.MenuPage;
import ui.BaseTest;
import utils.FileDownloader;
import utils.StringGenerator;
import utils.TestConstants;
import utils.data.CheckoutData;
import utils.data.LoginData;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountPageTest extends BaseTest {
    private LoginPage loginPage;
    private AccountBasePage accountBasePage;
    private AccountEditPage accountEditPage;
    private AccountInformationPage accountInformationPage;
    private AccountOrdersPage accountOrdersPage;
    private AccountViewOrderPage accountViewOrderPage;
    private CartPage cartPage;
    private MenuPage menuPage;
    private CheckoutDataFillingPage checkoutDataFillingPage;
    private HeaderComponent header;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(page);
        accountBasePage = new AccountBasePage(page);
        accountEditPage = new AccountEditPage(page);
        accountInformationPage = new AccountInformationPage(page);
        accountOrdersPage = new AccountOrdersPage(page);
        accountViewOrderPage = new AccountViewOrderPage(page);
        cartPage = new CartPage(page);
        menuPage = new MenuPage(page);
        checkoutDataFillingPage = new CheckoutDataFillingPage(page);
        header = new HeaderComponent(page);

        page.navigate(TestConstants.Urls.BASE_URL + TestConstants.Urls.MY_ACCOUNT_URL);

        loginPage.fillOutLoginForm(LoginData.EXISTING_EMAIL, LoginData.EXISTING_PASSWORD)
                .clickLoginButton();
    }

    @Test(description = "Загрузка файла")
    public void changeAccountImage() {
        Path filePath = FileDownloader.downloadFileToTemp("https://yt3.googleusercontent.com/ytc/AIdro_ljFZmd80sh4pvzQENH22j-J9HBKD1_hFa8hp2ga9DgtN4=s900-c-k-c0x00ffffff-no-rj",
                "Profile", ".png");

        accountBasePage.clickEditAccountLink();
        accountEditPage.fillUploadFileInput(filePath)
                .clickSaveAccountDetailsButton();
        String message = accountInformationPage.getMessage();

        assertThat(message)
                .as("Проверка сообщения об успешном изменении данных")
                .contains("Account details changed successfully.");
    }

    @Test(description = "Создание заказа и проверка его появления в заказах на странице пользователя")
    public void checkAddingOrder() {
        CheckoutFormData checkoutFormData = getCheckoutFormData();

        header.clickMenuPageButton();
        menuPage.addToCartFirstProduct();
        header.clickLinkToCart();

        cartPage.loadingProductTable();
        cartPage.clickProceedToPaymentButton();

        checkoutDataFillingPage.fillOutOrderDetails(checkoutFormData)
                .clickTermsCheckbox()
                .clickPlaceOrderButton();
        header.clickAccountPageButton();

        accountBasePage.clickOrdersAccountLink();
        accountOrdersPage.redirectToFirstOrder();
        String customerDetails = accountViewOrderPage.getCustomerDetails();

        assertThat(customerDetails)
                .as("Проверка, что детали заказа содержат имя пользователя")
                .contains(checkoutFormData.getFirstName());
    }

    private CheckoutFormData getCheckoutFormData() {
        return CheckoutFormData.builder()
                .firstName(StringGenerator.getUniqueString())
                .lastName(CheckoutData.LAST_NAME)
                .country(CheckoutData.COUNTRY)
                .address(CheckoutData.ADDRESS)
                .city(CheckoutData.CITY)
                .state(CheckoutData.STATE)
                .postcode(CheckoutData.POSTCODE)
                .phone(CheckoutData.PHONE)
                .build();
    }

}
