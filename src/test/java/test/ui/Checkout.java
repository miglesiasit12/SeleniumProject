package test.ui;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.*;
import utils.UiExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("ui")
@ExtendWith(UiExtension.class)
public class Checkout {

    @Test
    public void checkOutNotLoggedInWithBankWire(WebDriver driver) {
        WomenCategoryPage womenCategoryPage = new WomenCategoryPage(driver);
        AddedToCartPage addedToCartPage = new AddedToCartPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        AddressPage addressPage = new AddressPage(driver);
        ShippingPage shippingPage = new ShippingPage(driver);
        PaymentPage paymentPage = new PaymentPage(driver);

        womenCategoryPage.addFirstWomenProductToCart();
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(addedToCartPage.getProceedToCheckoutButton())).click();
        shoppingCartPage.getProceedToCheckoutButton().click();
        authenticationPage.login();
        addressPage.getProceedToCheckoutButton().click();
        shippingPage.getTermsOfServiceCheckBox().click();
        shippingPage.getProceedToCheckoutButton().click();
        paymentPage.getPayByBankWireButton().click();
        assertEquals("BANK-WIRE PAYMENT.", paymentPage.getPaymentTypeTitle().getText(), "Verify Bank Wire Payment is shown");

        paymentPage.getConfirmOrderButton().click();
        assertEquals("Your order on My Store is complete.", paymentPage.getCompleteOrderTitle().getText(), "Verify Bank Wire Payment is shown");
        Allure.getLifecycle().addAttachment("Checkout final page", "image/png",".png", ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
    }
}
