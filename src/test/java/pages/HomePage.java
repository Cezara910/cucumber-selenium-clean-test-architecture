package pages;

import org.openqa.selenium.By;

import static framework.Config.config;

public class HomePage extends BasePage<HomePage>{

    private static final By LOGIN_BTN_SELECTOR = By.cssSelector("a[href='https://vpl.bibliocommons.com/user_dashboard']");

    @Override
    public String pageTitle() {
        return "Home";
    }

    public void load(){
        navigateTo(config().url());
    }
    public LoginPage clickOnLoginButton(){
        elementLocatedBy(LOGIN_BTN_SELECTOR).click();
        return new LoginPage().waitForPageToLoad();
    }
}
