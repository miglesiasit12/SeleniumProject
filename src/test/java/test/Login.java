package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import page.HeaderBarPage;
import page.LoginPage;
import utils.UiExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(UiExtension.class)
public class Login {

    @Test
    public void loginAsExistingUser(WebDriver driver) {
        HeaderBarPage headerBarPage = new HeaderBarPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        headerBarPage.getLoginLink().click();
        loginPage.login();

        assertEquals("Michael Iglesias", headerBarPage.getUserNameInfo().getText(), "Validate the username matches the user that logged in");
    }

}
