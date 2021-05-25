package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.AddedToCartPage;
import page.ShoppingCartPage;
import page.WomenCategoryPage;
import utils.UiExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(UiExtension.class)
public class ShoppingCart {

    @Test
    public void addProductToCartTest(WebDriver driver) {
        WomenCategoryPage womenCategoryPage = new WomenCategoryPage(driver);
        AddedToCartPage addedToCartPage = new AddedToCartPage(driver);
        WebElement cartItemTitle = addedToCartPage.getCartItemTitle();

        womenCategoryPage.addFirstWomenProductToCart();
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(cartItemTitle));

        assertAll(
                () -> assertEquals("There is 1 item in your cart.", addedToCartPage.getCartProductHeader().getText(), "Verify 1 Item was added to cart"),
                () -> assertEquals("Faded Short Sleeve T-shirts", cartItemTitle.getText(), "Verify the correct item was added")
        );
    }

    @Test
    public void removeProductFromCart(WebDriver driver) {
        WomenCategoryPage womenCategoryPage = new WomenCategoryPage(driver);
        AddedToCartPage addedToCartPage = new AddedToCartPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        WebElement shoppingCartWarning = shoppingCartPage.getShoppingCartWarning();

        womenCategoryPage.addFirstWomenProductToCart();
        addedToCartPage.getProceedToCheckoutButton().click();
        shoppingCartPage.getTrashCanIcon().click();
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(shoppingCartWarning));

        assertEquals("Your shopping cart is empty.", shoppingCartWarning.getText(), "Verify shopping cart is emptied if only item is deleted");
    }

}
