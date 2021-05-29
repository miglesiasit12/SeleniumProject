package test.ui;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import test.ui.page.AccountPage;
import test.ui.page.AuthenticationPage;
import test.ui.page.CreateAnAccountPage;
import test.ui.page.HeaderBarPage;
import test.ui.utils.UiExtension;

import static org.junit.jupiter.api.Assertions.*;

@Tag("ui")
@ExtendWith(UiExtension.class)
public class AuthenticationTests {

    @Test
    public void loginAsExistingUser(WebDriver driver) {
        HeaderBarPage headerBarPage = new HeaderBarPage(driver);
        AuthenticationPage authenticationPage = new AuthenticationPage(driver);

        headerBarPage.getLoginLink().click();
        authenticationPage.login();

        assertEquals("Michael Iglesias", headerBarPage.getUserNameInfo().getText(), "Validate the username matches the user that logged in");
        Allure.getLifecycle().addAttachment("Logged in user", "image/png",".png", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
    }

    @Test
    public void registerNewUser(WebDriver driver) {
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(driver);
        AccountPage accountPage = new AccountPage(driver);

        createAnAccountPage.createAccount();
        assertAll("Validate elements on the account test.ui.page",
                () -> assertEquals("MY ACCOUNT", accountPage.getAccountTitle().getText(), "Validate Account Title Shows"),
                () -> assertTrue(accountPage.getMyAddressesButton().isDisplayed(), "Validate Address button is displayed"),
                () -> assertTrue(accountPage.getMyCreditSlipsButton().isDisplayed(), "Validate Credit slips button is displayed"),
                () -> assertTrue(accountPage.getMyPersonalInformationButton().isDisplayed(), "Validate My personal information button is displayed"),
                () -> assertTrue(accountPage.getMyWishlistsButton().isDisplayed(), "Validate My Wishlists button is displayed"),
                () -> assertTrue(accountPage.getOrderHistoryAndDetailsButton().isDisplayed(), "Validate Order History button is displayed")
        );
        Allure.addAttachment("Account Page", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64),".png");
        Allure.getLifecycle().addAttachment("Account Page", "image/png",".png", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
    }
}
