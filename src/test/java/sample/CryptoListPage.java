package sample;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;

public class CryptoListPage {

    private AppiumDriver<MobileElement> driver;
    private WebDriverWait wait;

    @AndroidFindBy(id = "com.example.automationexample:id/rv_currencies")
    private MobileElement table;

    @AndroidFindBy(id = "com.example.automationexample:id/cl_cell")
    private List<MobileElement> cells;

    public CryptoListPage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(new AppiumFieldDecorator(driver, ofSeconds(10L)), this);
    }

    public void navigateToFirstCryptoDetailScreen() {
        wait.until(ExpectedConditions.visibilityOf(table)).isDisplayed();
        cells.get(0).click();
    }
}
