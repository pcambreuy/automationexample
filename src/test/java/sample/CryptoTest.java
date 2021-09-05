package sample;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class CryptoTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    private CryptoListPage listPage;
    private CryptoDetailPage detailPage;

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

        listPage = new CryptoListPage(driver);
        detailPage = new CryptoDetailPage(driver);
    }

    @Test
    public void testCoinHasANumericPrice() {
        listPage.navigateToFirstCryptoDetailScreen();
        Assert.assertTrue(NumberUtils.isNumber(detailPage.getPrice()));
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}