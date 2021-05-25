package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import page.CategoryBarPage;
import page.CategoryPage;
import utils.UiExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(UiExtension.class)
public class SearchProducts {

    @Test
    public void searchByWomen(WebDriver driver){
        CategoryBarPage categoryBarPage = new CategoryBarPage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);

        categoryBarPage.getWomenCategoryButton().click();
        assertEquals("Women", categoryPage.getCategoryTitle().getText(), "Verify the Women category button returns the Women page");
    }

    @Test
    public void searchByDresses(WebDriver driver){
        CategoryBarPage categoryBarPage = new CategoryBarPage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);

        categoryBarPage.getDressesCategoryButton().click();

        assertEquals("Dresses", categoryPage.getCategoryTitle().getText(), "Verify the Dresses category button returns the Dresses page");
    }

    @Test
    public void searchByTShirts(WebDriver driver){
        CategoryBarPage categoryBarPage = new CategoryBarPage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);

        categoryBarPage.getTshirtsCategoryButton().click();

        assertEquals("T-shirts", categoryPage.getCategoryTitle().getText(), "Verify the T-Shirts category button returns the T-Shirts page");
    }
}
