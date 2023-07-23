package com.steps.stepDefinitions;

import com.steps.cucumber.AbstractSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginSteps extends AbstractSteps {
    private final WebDriver driver = testContext().getDriver();
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("#login button");

    @And("User enters email address as {string} and password as {string}")
    public void loginUserEntersEmailAddressAsAndPasswordAs(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
    }

    @And("Click on login button")
    public void loginClickOnLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Then("Page url should be {string}")
    public void loginPageUrlShouldBe(String expectedUrl) {
        Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
    }
}
