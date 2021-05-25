package page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class ShippingPage extends BasePage {

    public ShippingPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "cgv")
    private WebElement termsOfServiceCheckBox;

    @FindBy(name = "processCarrier")
    private WebElement proceedToCheckoutButton;

}
