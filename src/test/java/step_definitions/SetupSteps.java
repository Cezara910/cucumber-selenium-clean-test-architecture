package step_definitions;

import io.cucumber.java.en.Given;
import pages.HomePage;


public class SetupSteps {
    HomePage homePage;
    public SetupSteps(HomePage homePage) {
        this.homePage = homePage;
    }

    @Given("the user is on the main page")
    public void theUserIsOnTheMainPage() {
        homePage.load();
    }





}
