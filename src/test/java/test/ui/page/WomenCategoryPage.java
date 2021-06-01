package test.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WomenCategoryPage extends CategoryPage {

    public WomenCategoryPage(WebDriver driver) {
        super(driver);
    }

    public void addFirstWomenProductToCart(){
        Actions actions = new Actions(driver);
        driver.get("http://automationpractice.com/index.php?id_category=3&controller=category");
        WebElement firstProduct = getProductResultList().get(0);
        actions.moveToElement(firstProduct).perform();
        firstProduct.findElement(By.linkText("Add to cart")).click();
    }
}
