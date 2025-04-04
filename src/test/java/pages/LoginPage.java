package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import step_definitions.TestContext;

public class LoginPage extends BasePage<LoginPage>{
    private static final By INPUT_USERNAME_BARCODE = By.cssSelector("input[id^='user_name']");
    private static final By INPUT_PIN = By.cssSelector("input[id^='user_pin']");
    private static final By LOG_IN_BUTTON = By.cssSelector("input[testid='button_login']");
    public static final By LOGIN_ERROR_POPUP = By.cssSelector("div[class*='messaging displayMessage error']");
    @Override
    public String pageTitle() {
        return "Log In";
    }

    public void enterUserNameOrBarcode(String text){
        elementLocatedBy(INPUT_USERNAME_BARCODE).sendKeys(text);
    }
    public void enterPIN(String text){
        elementLocatedBy(INPUT_PIN).sendKeys(text);
    }
    public void clicksLogIn(){
        elementLocatedBy(LOG_IN_BUTTON).click();
    }
}
