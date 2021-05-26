package utils.webDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class RemoteWebDriverFactory {

    public static WebDriver getWebDriver(WebDriverType webDriverType) {
        RemoteWebDriver driver = null;
        boolean retry = false;
        int retryTimes = 0;

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setAcceptInsecureCerts(true);
        desiredCapabilities.setCapability("enableVNC", true);
        desiredCapabilities.setCapability("enableVideo", true);
        switch (webDriverType) {
            case CHROME -> desiredCapabilities.setBrowserName("chrome");
            case FIREFOX -> desiredCapabilities.setBrowserName("firefox");
            case EDGE -> desiredCapabilities.setBrowserName("edge");
        }
        do try {
            driver = new RemoteWebDriver(new URL(System.getenv("remoteWebDriverHost")), desiredCapabilities);
            driver.setFileDetector(new LocalFileDetector());
        } catch (Exception e) {
            e.printStackTrace();
            retry = true;
        } while (retry && retryTimes++ < 10);

        return driver;
    }
}
