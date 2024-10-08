package test.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.ui.page.*;
import test.ui.utils.UiExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("ui")
@Epic("UI Tests")
@Feature("Checkout Pages")
@ExtendWith(UiExtension.class)
public class CheckoutTests {

    @Test
    @Description("Checkout with and login with an existing user to pay with a bank wire and assert that the payment is confirmed")
    public void checkOutNotLoggedInWithBankWire(WebDriver driver) {
        WomenCategoryPage womenCategoryPage = new WomenCategoryPage(driver);
        AddedToCartPage addedToCartPage = new AddedToCartPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        AddressPage addressPage = new AddressPage(driver);
        ShippingPage shippingPage = new ShippingPage(driver);
        PaymentPage paymentPage = new PaymentPage(driver);

        //Add product to cart and proceed through the checkout process
        womenCategoryPage.addFirstWomenProductToCart();
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(addedToCartPage.getProceedToCheckoutButton())).click();
        shoppingCartPage.getProceedToCheckoutButton().click();
        authenticationPage.login();
        addressPage.getProceedToCheckoutButton().click();
        shippingPage.getTermsOfServiceCheckBox().click();
        shippingPage.getProceedToCheckoutButton().click();
        paymentPage.getPayByBankWireButton().click();
        assertEquals("BANK-WIRE PAYMENT.", paymentPage.getPaymentTypeTitle().getText(), "Verify Bank Wire Payment is shown");

        paymentPage.getConfirmOrderButton().click();
        assertEquals("Your order on My Store is complete.", paymentPage.getCompleteOrderTitle().getText(), "Verify Bank Wire Payment is shown");
        Allure.getLifecycle().addAttachment("Checkout final test.ui.page", "image/png",".png", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
    }
}
