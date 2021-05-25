package page;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class CategoryPage extends BasePage{

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "category-name")
    private WebElement categoryTitle;

    @FindBy(className = "#center_column > div.content_scene_cat > div > div > div")
    private WebElement categoryDescription;
}
