package sample;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;

public class CryptoTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        // Capabilities full list https://appium.io/docs/en/writing-running-appium/caps/

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "XXXXX");
        caps.setCapability("udid", "XXXX"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "XXXXX");
        caps.setCapability("platformVersion", "XXXX");
        // Reset strategies https://appium.io/docs/en/writing-running-appium/other/reset-strategies/index.html
        caps.setCapability("noReset", "false");

        // **** Android only ****

        caps.setCapability("skipUnlock", "true");

        // https://stackoverflow.com/questions/13193592/adb-android-getting-the-name-of-the-current-activity
        // adb shell dumpsys window windows | grep -E 'mObscuringWindow' For Android 29
        caps.setCapability("appPackage", "XXXXX");
        caps.setCapability("appActivity", "XXXXX");

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}