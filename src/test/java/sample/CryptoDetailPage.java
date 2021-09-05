package sample;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;

public class CryptoDetailPage {

    @AndroidFindBy(id = "com.example.automationexample:id/tv_price")
    private MobileElement price;
    private WebDriverWait wait;

    private AppiumDriver<MobileElement> driver;

    public CryptoDetailPage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);

        PageFactory.initElements(new AppiumFieldDecorator(driver, ofSeconds(10L)), this);
    }

    public String getPrice() {
        return wait.until(ExpectedConditions.visibilityOf(price)).getText();
    }

}
