package sample;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CryptoTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    By cookieButton = By.id("com.icemobile.jumboclient:id/bt_accept_all");


    @BeforeMethod
    public void setup() throws MalformedURLException {
        // Capabilities full list https://appium.io/docs/en/writing-running-appium/caps/

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel 3 API 29");
        caps.setCapability("udid", "emulator-5554"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10.0");
        // Reset strategies https://appium.io/docs/en/writing-running-appium/other/reset-strategies/index.html
        caps.setCapability("noReset", "false");

        // **** Android only ****

        caps.setCapability("skipUnlock", "true");

        // https://stackoverflow.com/questions/13193592/adb-android-getting-the-name-of-the-current-activity
        // adb shell dumpsys window windows | grep -E 'mObscuringWindow' For Android 29
        caps.setCapability("appPackage", "com.icemobile.jumboclient");
        caps.setCapability("appActivity", "com.jumbo.legacy.activity.CafMainActivity");

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testSmog() throws InterruptedException {
        try {
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.findElement(cookieButton).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(cookieButton));
            //...here we have Cookie
        } catch (TimeoutException e) {
            //...here we have not Cookie
        }
        Thread.sleep(10000);

    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}