package test.ui.page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class CatalogPage extends BasePage{

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#ul_layered_category_0 > li:nth-child(1) > label > a")
    private WebElement categoriesTopsCheckbox;

    @FindBy(css = "#ul_layered_id_attribute_group_1 > li:nth-child(1) > label > a")
    private WebElement sizeSCheckbox;

    @FindBy(css = "#ul_layered_id_attribute_group_3 > li:nth-child(4) > label > a")
    private WebElement colorOrangeCheckBox;
}
