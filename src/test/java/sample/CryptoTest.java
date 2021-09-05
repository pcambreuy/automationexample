package sample;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import org.apache.commons.lang3.math.NumberUtils;
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
    By cryptoPrice = By.id("com.example.automationexample:id/tv_price");
    By progress = By.id("com.example.automationexample:id/progress");

//    By animationBy      = By.id("com.isinolsun.app:id/animation_view");
//    By toolBarTitleBy   = By.id("com.isinolsun.app:id/toolbarTitle");

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
        caps.setCapability("appPackage", "com.example.automationexample");
        caps.setCapability("appActivity", "com.example.automationexample.MainActivity");

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);
        //setConnectionToON();
    }

    @Test
    public void testCoinHasANumericPrice() {

        wait.until(ExpectedConditions.presenceOfElementLocated(cryptoTable));
        List<MobileElement> allCells = driver.findElements(cryptoCell);
        MobileElement firstCell = allCells.get(0).findElement(cryptoName);
        firstCell.click();

        //Detail Screen
        String price = wait.until(ExpectedConditions.visibilityOfElementLocated(cryptoPrice)).getText();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(progress));
        // Obs:
        // Assert.assertFalse(driver.findElement(progress).isDisplayed()); ==> Crash org.openqa.selenium.NoSuchElementException
        Assert.assertFalse(price.isEmpty());
        Assert.assertTrue(NumberUtils.isNumber(price));
        //Obs: This test is doing more than just verify if the price is numeric
    }

    private void setConnectionToOFF() {
        driver.setConnection(driver.setConnection(new ConnectionStateBuilder()
                .withWiFiDisabled()
                .withDataDisabled()
                .build()));
    }

    private void setConnectionToON() {
        driver.setConnection(new ConnectionStateBuilder()
                .withWiFiEnabled()
                .withDataEnabled()
                .build());
    }

    @Test
    public void testAnErrorMessageIsDisplayedIfThereIsAnError() {
        setConnectionToOFF();
        wait.until(ExpectedConditions.presenceOfElementLocated(cryptoTable));
        List<MobileElement> allCells = driver.findElements(cryptoCell);
        MobileElement firstCell = allCells.get(0).findElement(cryptoName);
        firstCell.click();

        //Detail Screen
        String price = wait.until(ExpectedConditions.visibilityOfElementLocated(cryptoPrice)).getText();

        Assert.assertEquals(price, "ERROR", "Error message is not displayed");
    }

    @AfterMethod
    public void teardown() {
        setConnectionToON();
        driver.quit();
    }
}