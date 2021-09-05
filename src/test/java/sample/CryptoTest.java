package sample;

import io.appium.java_client.MobileBy;
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
import java.util.List;

public class CryptoTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    By cryptoName = By.id("com.example.automationexample:id/tv_name");
    By cryptoTable = By.id("com.example.automationexample:id/rv_currencies");
    By cryptoCell = By.id("com.example.automationexample:id/cl_cell");
//    By animationBy      = By.id("com.isinolsun.app:id/animation_view");
//    By toolBarTitleBy   = By.id("com.isinolsun.app:id/toolbarTitle");

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

//    @Test
//    public void testFirstCell() throws InterruptedException {
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(cryptoTable));
//
//        List<MobileElement> allCells = driver.findElements(cryptoCell);
//        MobileElement firstCell = allCells.get(0).findElement(cryptoName);
//
//        String cryptoName = firstCell.getText();
//        Assert.assertEquals(cryptoName, "BTC", "First crypto item is not correct");
//
//        //Note: this will only return one element, the recyclerview
//        // List<MobileElement> elements = driver.findElements(cryptoTable);
//        // MobileElement btcCell = elements.get(0);
//    }

    @Test
    public void checkLastCell() {
        //http://appium.io/docs/en/writing-running-appium/tutorial/swipe/android-simple/
        wait.until(ExpectedConditions.visibilityOfElementLocated(cryptoTable));

        MobileElement element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().resourceIdMatches(\".*tv_name.*\").text(\"RUNE\"))"));

        Assert.assertNotNull(element);

    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}