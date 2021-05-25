package page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
    private WebElement emailAddressTextBox;

    @FindBy(id = "passwd")
    private WebElement passwordTextBox;

    @FindBy(id = "SubmitLogin")
    private WebElement loginButton;

    public void login() {
        getEmailAddressTextBox().sendKeys("michaeliglesiasit@gmail.com");
        getPasswordTextBox().sendKeys("&ZRb5ekckHE&");
        getLoginButton().click();
    }
}
