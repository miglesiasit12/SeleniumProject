package test.ui.utils.webDriver;

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
        String url = System.getenv("remoteWebDriverHost");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setAcceptInsecureCerts(true);
        desiredCapabilities.setCapability("enableVNC", true);
        desiredCapabilities.setCapability("enableVideo", true);
        switch (webDriverType) {
            case CHROME:
                desiredCapabilities.setBrowserName("chrome");
                break;
            case FIREFOX:
                desiredCapabilities.setBrowserName("firefox");
                break;
            case EDGE:
                desiredCapabilities.setBrowserName("edge");
                break;
        }
        System.out.println("Connecting to: " + url);
        do try {
            driver = new RemoteWebDriver(new URL(url), desiredCapabilities);
            driver.setFileDetector(new LocalFileDetector());
        } catch (Exception e) {
            e.printStackTrace();
            retry = true;
        } while (retry && retryTimes++ < 10);

        return driver;
    }
}
