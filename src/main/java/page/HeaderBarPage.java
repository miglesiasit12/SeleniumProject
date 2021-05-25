package page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class HeaderBarPage extends BasePage {

    public HeaderBarPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText = "Sign in")
    public WebElement loginLink;

    @FindBy(css = "#header > div.nav > div > div > nav > div:nth-child(1) > a > span")
    public WebElement userNameInfo;
}
