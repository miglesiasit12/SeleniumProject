package utils;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.webDriver.RemoteWebDriverFactory;
import utils.webDriver.WebDriverFactory;
import utils.webDriver.WebDriverType;

import java.util.concurrent.TimeUnit;

public class UiExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == WebDriver.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return this.driver.get();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        WebDriver webDriver;
        if (Boolean.valueOf(System.getenv().get("isRemoteDriverRun")).equals(true)){
            webDriver = RemoteWebDriverFactory.getWebDriver(WebDriverType.valueOf(System.getenv().get("browserType")));
        } else {
            webDriver = WebDriverFactory.getWebDriver(WebDriverType.valueOf(System.getenv().get("browserType")));
        }

        webDriver.get("http://www.automationpractice.com/");
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.set(webDriver);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (context.getExecutionException().isPresent()){
            Allure.getLifecycle().addAttachment("Test Failure", "image/png",".png", ((TakesScreenshot)driver.get()).getScreenshotAs(OutputType.BYTES));
        }
        driver.get().close();
    }
}
