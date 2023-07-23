package com.steps.stepDefinitions;

import com.steps.cucumber.AbstractSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class CommonSteps extends AbstractSteps {
    private final WebDriver driver = testContext().getDriver();

    @When("User go to url {string}")
    public void user_go_to_url(String url) {
        driver.get(url);
    }


    @Then("Verify webpage title is {string}")
    public void verifyWebpageTitleIs(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }
}
