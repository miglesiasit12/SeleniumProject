package page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class ShoppingCartPage extends BasePage {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "1_1_0_0")
    private WebElement trashCanIcon;

    @FindBy(css = "#center_column > p")
    private WebElement shoppingCartWarning;

    @FindBy(css = "#center_column > p.cart_navigation.clearfix > a.button.btn.btn-default.standard-checkout.button-medium")
    private WebElement proceedToCheckoutButton;
}
