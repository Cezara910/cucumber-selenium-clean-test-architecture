package step_definitions;

import io.cucumber.java.After;

public class CleanupSteps {
    TestContext testContext;
    public CleanupSteps(TestContext testContext) {
        this.testContext = testContext;
    }
    @After
    public void tearDown(){
        testContext.quitDriver();
    }
}
