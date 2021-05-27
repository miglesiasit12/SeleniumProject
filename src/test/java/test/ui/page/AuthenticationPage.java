package test.ui.page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class AuthenticationPage extends BasePage {

    public AuthenticationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
    private WebElement loginEmailAddressTextBox;

    @FindBy(id = "passwd")
    private WebElement loginPasswordTextBox;

    @FindBy(id = "SubmitLogin")
    private WebElement signInButton;

    @FindBy(id = "email_create")
    private WebElement newEmailAddressTextBox;

    @FindBy(id = "SubmitCreate")
    private WebElement createAnAccountButton;

    public void login() {
        getLoginEmailAddressTextBox().sendKeys("michaeliglesiasit@gmail.com");
        getLoginPasswordTextBox().sendKeys("&ZRb5ekckHE&");
        getSignInButton().click();
    }
}
