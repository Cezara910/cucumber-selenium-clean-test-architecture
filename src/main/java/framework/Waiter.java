package framework;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

import static java.lang.String.format;
import static framework.Config.config;

public class Waiter {
    Duration LOAD_WAITING_TIME = Duration.ofSeconds(config().loadingTimeout());
    WebDriver driver;
    public Waiter(WebDriver driver){
        this.driver = driver;
    }
    public void waitForPageWithURLToLoad(String url){
        new FluentWait<>(driver)
                .withMessage(format("Page with URL %s did not load", url))
                .withTimeout(LOAD_WAITING_TIME)
                .until(webDriver -> {Logger.getLogger().info(webDriver.getCurrentUrl());
                    return webDriver.getCurrentUrl().equals(url);});

    }
    public void waitForPageWitTitleToLoad(String pageTitle){
        new FluentWait<>(driver)
                .withMessage(format("Page with title %s did not load", pageTitle))
                .withTimeout(LOAD_WAITING_TIME)
                .until(webDriver -> webDriver.getTitle().contains(pageTitle));
    }
    public WebElement waitForElement(By locatedBy) {
        return new FluentWait<>(driver)
                .withTimeout(LOAD_WAITING_TIME)
                .ignoring(NoSuchElementException.class)
                .until(d -> {
                    return driver.findElement(locatedBy);
                });
    }
    public void waitForCondition(ExpectedCondition<?> expectedCondition, int timeout){
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                        .until(expectedCondition);

    }
}
