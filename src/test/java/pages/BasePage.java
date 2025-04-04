package pages;

import framework.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import framework.Waiter;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class BasePage<T extends BasePage<T>> {
    WebDriver driver;
    private final Waiter wait;

   public BasePage(){
        driver = DriverManager.getDriver();
        this.wait = new Waiter(driver);
    }


    public Waiter getWait() {
        return wait;
    }
    public T waitForPageToLoad(){
       wait.waitForPageWitTitleToLoad(pageTitle());
       return self();
    }
    public void navigateTo(String url){
       driver.navigate().to(url);
       wait.waitForPageWithURLToLoad(url);
   }
   public WebElement elementLocatedBy(By locatedBy){
       return wait.waitForElement(locatedBy);

   }
   public abstract String pageTitle();
   @SuppressWarnings("unchecked")
   private T self(){
       return (T)this;
   }


}
