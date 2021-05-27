package test.ui.page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class AddressPage extends BasePage {

    public AddressPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "processAddress")
    private WebElement proceedToCheckoutButton;

}
