package page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "bankwire")
    private WebElement payByBankWireButton;

    @FindBy(className = "cheque")
    private WebElement payByCheck;

    @FindBy(css = "#center_column > form > div > h3")
    private WebElement paymentTypeTitle;

    @FindBy(css ="#cart_navigation > button > span")
    private WebElement confirmOrderButton;

    @FindBy(css = "#center_column > div > p > strong")
    private WebElement completeOrderTitle;



}
