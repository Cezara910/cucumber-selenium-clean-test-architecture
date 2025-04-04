package step_definitions;

import framework.DriverManager;
import org.openqa.selenium.WebDriver;

public class TestContext {
    private WebDriver driver;
    public WebDriver getDriver() {
        return driver;
    }
    public void setDriver(){
        driver = DriverManager.getDriver();
    }
    public void quitDriver(){
        DriverManager.quitDriver();
    }
}
