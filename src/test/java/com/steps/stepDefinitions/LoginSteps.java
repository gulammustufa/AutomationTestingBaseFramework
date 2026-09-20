package com.steps.stepDefinitions;

import com.microsoft.playwright.Page;
import com.steps.cucumber.BaseSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps extends BaseSteps {
    Page page = testContext().getBrowserPage();
    String usernameFieldLocator = "#username";
    String passwordFieldLocator = "#password";
    String loginButton = "//button[@type='submit']";
    String successMessageLocator = "//div[@id='flash-messages']/div";
    String errorMessageLocator = "//div[@id='flash-messages']/div";

    @When("User goes to login page")
    public void userGoesToLoginPage() {
        page.getByText("Form Authentication").click();
    }

    @And("User enters email address as {string} and password as {string}")
    public void loginUserEntersEmailAddressAsAndPasswordAs(String username, String password) {
        page.locator(usernameFieldLocator).fill(username);
        page.locator(passwordFieldLocator).fill(password);
    }

    @And("Click on login button")
    public void loginClickOnLoginButton() {
        page.locator(loginButton).click();
    }

    @Then("Message should be {string}")
    public void messageShouldBe(String message) {
        String actualMessage = page.locator(successMessageLocator).textContent().strip().replace("\n×", "").replace("\n?", "");
        assertThat(actualMessage).isEqualTo(message);
    }

    @Then("Validation message should be {string}")
    public void validationMessageShouldBe(String message) {
        String actualMessage = page.locator(errorMessageLocator).textContent().strip().replace("\n×", "").replace("\n?", "");
        assertThat(actualMessage).contains(message);
    }
}
