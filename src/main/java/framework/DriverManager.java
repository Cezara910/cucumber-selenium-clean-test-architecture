package framework;

import org.openqa.selenium.WebDriver;

import static framework.Config.config;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver getDriver() {
        if(driver.get() == null)
           initializeDriver();
        return driver.get();
    }

    private static void initializeDriver() {
        String target = config().target().toUpperCase();
        String browserName = config().browser().toUpperCase();

        WebDriver webDriver;
        if (Target.valueOf(target) == Target.CONTAINERS) {
            webDriver = BrowserType.valueOf(browserName).getContainerDriver();
        } else {
            webDriver = BrowserType.valueOf(browserName).getLocalDriver();
        }

        driver.set(webDriver);
    }

    public static void quitDriver(){
        driver.get().quit();
        if(Target.valueOf(config().target().toUpperCase()).equals(Target.CONTAINERS))
            ContainerManager.stopContainers();
        driver.remove();
    }
}
