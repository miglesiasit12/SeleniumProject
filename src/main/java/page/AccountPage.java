package page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class AccountPage extends BasePage {

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#center_column > h1")
    private WebElement accountTitle;

    @FindBy(css = "#center_column > div > div:nth-child(1) > ul > li:nth-child(1) > a > span")
    private WebElement orderHistoryAndDetailsButton;

    @FindBy(css = "#center_column > div > div:nth-child(1) > ul > li:nth-child(2) > a > span")
    private WebElement myCreditSlipsButton;

    @FindBy(css = "#center_column > div > div:nth-child(1) > ul > li:nth-child(3) > a > span")
    private WebElement myAddressesButton;

    @FindBy(css = "#center_column > div > div:nth-child(1) > ul > li:nth-child(4) > a > span")
    private WebElement myPersonalInformationButton;

    @FindBy(css = "#center_column > div > div:nth-child(2) > ul > li > a > span")
    private WebElement myWishlistsButton;

}
