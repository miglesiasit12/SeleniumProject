package test.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.ui.page.*;
import test.ui.utils.UiExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("ui")
@Epic("UI Tests")
@Feature("Search Pages")
@ExtendWith(UiExtension.class)
public class SearchProductsTests {

    @BeforeEach
    public void pickFirstProduct(WebDriver driver) {
        driver.get("http://automationpractice.com/index.php?id_product=1&controller=product");
    }

    @Test
    @Description("Search for a product using three criteria on the category page and assert it matches the criteria")
    public void searchWithThreeCriteria(WebDriver driver) {
        CategoryBarPage categoryBarPage = new CategoryBarPage(driver);
        CatalogPage catalogPage = new CatalogPage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);
        AddedToCartPage addedToCartPage = new AddedToCartPage(driver);
        WebDriverWait wait =  new WebDriverWait(driver, 15);
        Actions actions = new Actions(driver);
        WebElement firstProduct;
        String productName;
        String sizeAndColor;

        //Navigate to product list and Select criteria
        categoryBarPage.getWomenCategoryButton().click();
        catalogPage.getColorOrangeCheckBox().click();
        catalogPage.getSizeSCheckbox().click();
        catalogPage.getCategoriesTopsCheckbox().click();
        firstProduct = categoryPage.getProductResultList().get(0);
        actions.moveToElement(firstProduct).perform();
        wait.until(ExpectedConditions.elementToBeClickable(firstProduct.findElement(By.linkText("Add to cart")))).click();
        wait.until(ExpectedConditions.visibilityOf(addedToCartPage.getCartItemTitle()));
        productName = addedToCartPage.getCartItemTitle().getText();
        sizeAndColor = addedToCartPage.getProductAttributes().getText();

        assertEquals("Faded Short Sleeve T-shirts", productName, "Product matches category");
        assertEquals("Orange, S", sizeAndColor, "Product matches selected color and size");
        Allure.getLifecycle().addAttachment("Women Category", "image/png", ".png", ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
    }
}
