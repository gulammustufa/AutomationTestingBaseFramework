package com.steps.stepDefinitions;

import com.steps.cucumber.AbstractSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginSteps extends AbstractSteps {
    private final WebDriver driver = testContext().getDriver();
    private final By loginLinkLocator = By.linkText("Form Authentication");
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("#login button");
    private final By successMessageLocator = By.xpath("//div[@id='flash-messages']/div");
    private final By errorMessageLocator = By.xpath("//div[@id='flash-messages']/div");

    @When("User goes to login page")
    public void userGoesToLoginPage() {
        driver.findElement(loginLinkLocator).click();
    }

    @And("User enters email address as {string} and password as {string}")
    public void loginUserEntersEmailAddressAsAndPasswordAs(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
    }

    @And("Click on login button")
    public void loginClickOnLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Then("Message should be {string}")
    public void messageShouldBe(String message) {
        String actualMessage = driver.findElement(successMessageLocator).getText().strip().replace("\n×","");
        Assert.assertEquals(message, actualMessage);
    }

    @Then("Validation message should be {string}")
    public void validationMessageShouldBe(String message) {
        String actualMessage = driver.findElement(errorMessageLocator).getText().strip().replace("\n×","");
        Assert.assertEquals(message, actualMessage);
    }
}
