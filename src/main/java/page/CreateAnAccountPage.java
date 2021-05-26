package page;

import com.github.javafaker.Faker;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import util.User;

import java.util.UUID;

@Getter
public class CreateAnAccountPage extends BasePage {

    public CreateAnAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "id_gender1")
    private WebElement titleMrRadioButton;

    @FindBy(id = "customer_firstname")
    private WebElement personalInfoFirstNameTextBox;

    @FindBy(id = "customer_lastname")
    private WebElement personalInfoLastNameTextBox;

    @FindBy(id = "passwd")
    private WebElement passwordTextBox;

    @FindBy(id = "days")
    private WebElement dateOfBirthDayDropdown;

    @FindBy(id = "months")
    private WebElement dateOfBirthMonthDropdown;

    @FindBy(id = "years")
    private WebElement dateOfBirthDayYear;

    @FindBy(id = "newsletter")
    private WebElement signUpForNewsLetterCheckBox;

    @FindBy(id = "uniform-optin")
    private WebElement specialOffersCheckBox;

    @FindBy(id = "firstname")
    private WebElement addressFirstNameTextBox;

    @FindBy(id = "lastname")
    private WebElement addressLastNameTextBox;

    @FindBy(id = "company")
    private WebElement companyTextBox;

    @FindBy(id = "address1")
    private WebElement addressTextBox;

    @FindBy(id = "address2")
    private WebElement addressLineTwoTextBox;

    @FindBy(id = "city")
    private WebElement cityTextBox;

    @FindBy(id = "id_state")
    private WebElement stateDropDown;

    @FindBy(id = "postcode")
    private WebElement zipCodeTextBox;

    @FindBy(id = "id_country")
    private WebElement countryDropDown;

    @FindBy(id = "other")
    private WebElement additionalInformationTextBox;

    @FindBy(id = "phone")
    private WebElement homePhoneTextBox;

    @FindBy(id = "phone_mobile")
    private WebElement mobilePhoneTextBox;

    @FindBy(id = "alias")
    private WebElement addressAliasTextBox;

    @FindBy(id = "submitAccount")
    private WebElement registerButton;

    public User createAccount() {
        Faker faker = new Faker();
        HeaderBarPage headerBarPage = new HeaderBarPage(driver);
        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        String emailAddress = faker.internet().emailAddress();
        String password = faker.internet().password(6,31);
        User user = new User(emailAddress, password);

        headerBarPage.getLoginLink().click();
        authenticationPage.getNewEmailAddressTextBox().sendKeys(emailAddress);
        authenticationPage.getCreateAnAccountButton().click();

        getTitleMrRadioButton().click();
        getPersonalInfoFirstNameTextBox().sendKeys(faker.name().firstName());
        getPersonalInfoLastNameTextBox().sendKeys(faker.name().lastName());
        getPasswordTextBox().sendKeys(password);
        new Select(getDateOfBirthDayDropdown()).selectByValue("1");
        new Select(getDateOfBirthMonthDropdown()).selectByValue("12");
        new Select(getDateOfBirthDayYear()).selectByValue("1980");
        getSignUpForNewsLetterCheckBox().click();
        getSpecialOffersCheckBox().click();
        getCompanyTextBox().sendKeys(faker.company().name());
        getAddressTextBox().sendKeys(faker.address().streetAddress());
        getAddressLineTwoTextBox().sendKeys(faker.address().streetAddressNumber());
        getCityTextBox().sendKeys(faker.address().city());
        new Select(getStateDropDown()).selectByVisibleText(faker.address().state());
        getZipCodeTextBox().sendKeys("33521");
        new Select(getCountryDropDown()).selectByVisibleText("United States");
        getAdditionalInformationTextBox().sendKeys(faker.leagueOfLegends().rank());
        getHomePhoneTextBox().sendKeys(faker.phoneNumber().cellPhone());
        getMobilePhoneTextBox().sendKeys(faker.phoneNumber().cellPhone());
        getAddressAliasTextBox().clear();
        getAddressAliasTextBox().sendKeys(faker.animal().name());
        getRegisterButton().click();

        return user;
    }
}
