package framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

public enum BrowserType {
    CHROME{
        @Override
        public WebDriver getLocalDriver()
        {
            return WebDriverManager.chromedriver()
                    .clearDriverCache()
                    .capabilities(getOptions())
                    .create();
        }

        @Override
        public ChromeOptions getOptions() {
            return new ChromeOptions()
            .addArguments("--start-maximized");
        }
    },
    FIREFOX{
        @Override
        public WebDriver getLocalDriver(){
            return WebDriverManager.firefoxdriver().create();
        }

        @Override
        public FirefoxOptions getOptions() {
            return new FirefoxOptions();
//                    .addArguments("--headless");
        }
    },
    EDGE{
        @Override
        public WebDriver getLocalDriver(){
            return WebDriverManager.edgedriver().create();
        }

        @Override
        public EdgeOptions getOptions() {
            return new EdgeOptions().addArguments("--start-maximized");
        }
    };

    public abstract WebDriver getLocalDriver();
    public WebDriver getContainerDriver(){
        BrowserWebDriverContainer<?> container =  ContainerManager.provideContainer(this);
        container.start();
        return new RemoteWebDriver(container.getSeleniumAddress(), getOptions());
    }
    public abstract Capabilities getOptions();

}
