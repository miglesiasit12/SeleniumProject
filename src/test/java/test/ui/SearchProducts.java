package test.ui;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import test.ui.page.CategoryBarPage;
import test.ui.page.CategoryPage;
import test.ui.utils.UiExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("ui")
@ExtendWith(UiExtension.class)
public class SearchProducts {

    @BeforeEach
    public void pickFirstProduct(WebDriver driver){
        driver.get("http://automationpractice.com/index.php?id_product=1&controller=product");
    }

    @Test
    public void searchByWomen(WebDriver driver){
        CategoryBarPage categoryBarPage = new CategoryBarPage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);

        categoryBarPage.getWomenCategoryButton().click();
        assertEquals("Women", categoryPage.getCategoryTitle().getText(), "Verify the Women category button returns the Women test.ui.page");
        Allure.getLifecycle().addAttachment("Women Category", "image/png",".png", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
    }

    @Test
    public void searchByDresses(WebDriver driver){
        CategoryBarPage categoryBarPage = new CategoryBarPage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);

        categoryBarPage.getDressesCategoryButton().click();
        assertEquals("Dresses", categoryPage.getCategoryTitle().getText(), "Verify the Dresses category button returns the Dresses test.ui.page");
        Allure.getLifecycle().addAttachment("Dress Category", "image/png",".png", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
    }

    @Test
    public void searchByTShirts(WebDriver driver){
        CategoryBarPage categoryBarPage = new CategoryBarPage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);

        categoryBarPage.getTshirtsCategoryButton().click();
        assertEquals("T-shirts", categoryPage.getCategoryTitle().getText(), "Verify the T-Shirts category button returns the T-Shirts test.ui.page");
        Allure.getLifecycle().addAttachment("T-Shirt Category", "image/png",".png", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
    }
}
