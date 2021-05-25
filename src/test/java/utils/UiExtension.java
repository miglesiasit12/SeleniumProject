package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.get("http://www.automationpractice.com/");
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.set(webDriver);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        driver.get().close();
    }
}
