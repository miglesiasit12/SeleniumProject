package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import page.*;
import utils.UiExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(UiExtension.class)
public class Checkout {

    @Test
    public void checkOutNotLoggedInWithBankWire(WebDriver driver) {
        WomenCategoryPage womenCategoryPage = new WomenCategoryPage(driver);
        AddedToCartPage addedToCartPage = new AddedToCartPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        AddressPage addressPage = new AddressPage(driver);
        ShippingPage shippingPage = new ShippingPage(driver);
        PaymentPage paymentPage = new PaymentPage(driver);

        womenCategoryPage.addFirstWomenProductToCart();
        addedToCartPage.getProceedToCheckoutButton().click();
        shoppingCartPage.getProceedToCheckoutButton().click();
        loginPage.login();
        addressPage.getProceedToCheckoutButton().click();
        shippingPage.getTermsOfServiceCheckBox().click();
        shippingPage.getProceedToCheckoutButton().click();
        paymentPage.getPayByBankWireButton().click();
        assertEquals("BANK-WIRE PAYMENT.", paymentPage.getPaymentTypeTitle().getText(), "Verify Bank Wire Payment is shown");
        paymentPage.getConfirmOrderButton().click();
        assertEquals("Your order on My Store is complete.", paymentPage.getCompleteOrderTitle().getText(), "Verify Bank Wire Payment is shown");
    }
}
