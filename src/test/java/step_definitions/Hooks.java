package step_definitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

import static java.lang.String.format;

public class Hooks {
    TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }
    @Before
    public void setUp(){
        testContext.setDriver();
    }

    @After
    public void captureScreenShotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = testContext.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(format("%s_%s", LocalDateTime.now(), driver.getTitle()), new ByteArrayInputStream(screenshot));
        }
    }
}
