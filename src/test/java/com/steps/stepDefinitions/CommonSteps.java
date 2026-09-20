package com.steps.stepDefinitions;

import com.microsoft.playwright.Page;
import com.steps.cucumber.BaseSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps extends BaseSteps {
    Page page = testContext().getBrowserPage();

    @When("User go to url {string}")
    public void user_go_to_url(String url) {
        page.navigate(url);
    }

    @Then("Verify webpage title is {string}")
    public void verifyWebpageTitleIs(String expectedTitle) {
        String actualTitle = page.title();
        assertThat(actualTitle).isEqualTo(expectedTitle);
    }
}
