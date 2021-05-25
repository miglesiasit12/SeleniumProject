package page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class CategoryBarPage extends BasePage {

    public CategoryBarPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#block_top_menu > ul > li:nth-child(1) > a")
    private WebElement womenCategoryButton;

    @FindBy(css = "#block_top_menu > ul > li:nth-child(2) > a")
    private WebElement dressesCategoryButton;

    @FindBy(css = "#block_top_menu > ul > li:nth-child(3) > a")
    private WebElement tshirtsCategoryButton;

}
