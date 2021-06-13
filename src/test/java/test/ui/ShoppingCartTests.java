package test.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.ui.page.AddedToCartPage;
import test.ui.page.ShoppingCartPage;
import test.ui.page.WomenCategoryPage;
import test.ui.utils.UiExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("ui")
@Epic("UI Tests")
@Feature("Shopping Cart Pages")
@ExtendWith(UiExtension.class)
public class ShoppingCartTests {

    @Test
    public void addProductToCartTest(WebDriver driver) {
        WomenCategoryPage womenCategoryPage = new WomenCategoryPage(driver);
        AddedToCartPage addedToCartPage = new AddedToCartPage(driver);
        WebElement cartItemTitle = addedToCartPage.getCartItemTitle();

        womenCategoryPage.addFirstWomenProductToCart();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(cartItemTitle));

        assertAll("Validate item in cart",
                () -> assertEquals("There is 1 item in your cart.", addedToCartPage.getCartProductHeader().getText(), "Verify 1 Item was added to cart"),
                () -> assertEquals("Faded Short Sleeve T-shirts", cartItemTitle.getText(), "Verify the correct item was added")
        );
        Allure.addAttachment("Shopping Cart", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64),".png");
    }

    @Test
    public void removeProductFromCart(WebDriver driver) {
        WomenCategoryPage womenCategoryPage = new WomenCategoryPage(driver);
        AddedToCartPage addedToCartPage = new AddedToCartPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        WebElement shoppingCartWarning = shoppingCartPage.getShoppingCartWarning();

        womenCategoryPage.addFirstWomenProductToCart();
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(addedToCartPage.getProceedToCheckoutButton())).click();
        shoppingCartPage.getTrashCanIcon().click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(shoppingCartWarning));

        assertEquals("Your shopping cart is empty.", shoppingCartWarning.getText(), "Verify shopping cart is emptied if only item is deleted");
        Allure.getLifecycle().addAttachment("Shopping Cart", "image/png",".png", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
    }

}
