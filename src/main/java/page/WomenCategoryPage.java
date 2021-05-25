package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class WomenCategoryPage extends CategoryPage {

    public WomenCategoryPage(WebDriver driver) {
        super(driver);
    }

    public void addFirstWomenProductToCart(){
        Actions actions = new Actions(driver);
        WebElement firstProduct;
        driver.get("http://automationpractice.com/index.php?id_category=3&controller=category");
        firstProduct = getProductResultList().get(0);
        actions.moveToElement(firstProduct).perform();
        firstProduct.findElement(By.linkText("Add to cart")).click();
    }
}
