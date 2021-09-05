package sample;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CryptoTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    By cryptoCell = By.id("com.example.automationexample:id/cl_cell");

    @BeforeMethod
    public void setup() throws MalformedURLException {
        // Capabilities full list https://appium.io/docs/en/writing-running-appium/caps/

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel 4 API 30");
        caps.setCapability("udid", "emulator-5556"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11.0");
        // Reset strategies https://appium.io/docs/en/writing-running-appium/other/reset-strategies/index.html
        caps.setCapability("noReset", "false");

        // **** Android only ****

        caps.setCapability("skipUnlock", "true");

        // https://stackoverflow.com/questions/13193592/adb-android-getting-the-name-of-the-current-activity
        // adb shell dumpsys window windows | grep -E 'mObscuringWindow' For Android 29
        caps.setCapability("appPackage", "com.example.automationexample");
        caps.setCapability("appActivity", "com.example.automationexample.MainActivity");

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void waitForRecyclerViewWithStaticWait() throws InterruptedException {
        Thread.sleep(6000);
        Assert.assertFalse(driver.findElements(cryptoCell).isEmpty());
    }

    @Test
    public void waitForRecyclerViewWitImplicitWait() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Assert.assertFalse(driver.findElements(cryptoCell).isEmpty());
        driver.findElements(cryptoCell).get(0).click();
    }

    @Test
    public void waitForRecyclerViewWitExplicitWait() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cryptoCell));
        Assert.assertFalse(driver.findElements(cryptoCell).isEmpty());
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}