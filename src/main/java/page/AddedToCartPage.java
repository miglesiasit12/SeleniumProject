package page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class AddedToCartPage extends BasePage {

    public AddedToCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > h2 > span.ajax_cart_product_txt")
    private WebElement cartProductHeader;

    @FindBy(id = "layer_cart_product_title")
    private WebElement cartItemTitle;

    @FindBy(css = "#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > a")
    private WebElement proceedToCheckoutButton;
}
