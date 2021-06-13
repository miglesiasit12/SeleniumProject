package test.ui.page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class ProductPage extends BasePage{

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css ="#layer_cart_product_attributes")
    WebElement productAttributes;

    @FindBy(css = "#layer_cart_product_title")
    WebElement productTitle;
}
