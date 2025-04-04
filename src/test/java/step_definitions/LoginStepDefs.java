package step_definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.HomePage;
import pages.LoginPage;

import static framework.DataGenerator.generateDigitAndLetterText;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static pages.LoginPage.LOGIN_ERROR_POPUP;

public class LoginStepDefs {
    HomePage homePage;
    LoginPage loginPage;

    public LoginStepDefs(HomePage homePage) {
        this.homePage = homePage;
    }

    @And("clicks on Log In")
    public void clicksOnLogIn() {
        loginPage = homePage.clickOnLoginButton();
    }

    @When("user enters invalid barcode")
    public void userEntersInvalidBarcode() {
        loginPage.enterUserNameOrBarcode(generateDigitAndLetterText(5));
    }

    @And("invalid PIN")
    public void invalidPIN() {
        loginPage.enterPIN(generateDigitAndLetterText(5));
    }

    @Then("pop-up with reject message appears")
    public void popUpWithRejectMessageAppears() {
        loginPage.getWait().waitForElement(LOGIN_ERROR_POPUP).isDisplayed();
    }

    @And("the pop-up fades after {int} seconds")
    public void thePopUpFadesAfterSeconds(int timeout) {
        loginPage.getWait().waitForCondition(
                invisibilityOfElementLocated(LOGIN_ERROR_POPUP), timeout);
    }

    @And("clicks on Log In button")
    public void clicksOnLogInButton() {
        loginPage.clicksLogIn();
    }
}
