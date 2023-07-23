package com.steps.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks extends AbstractSteps {
    @Before(order = 0)
    public void setUp(Scenario scenario) {
        testContext().setScenarioLogger(scenario);
        testContext().getScenarioLogger().log("SETUP SCENARIO " + scenario.getName());
    }

    @Before(value = "@driver", order = 1)
    public void setUpBrowser() {
        testContext().openBrowser();
    }

    @After()
    public void tearDown(Scenario scenario) {
        testContext().getScenarioLogger().log("GENERIC TEARDOWN " + scenario.getName());
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) testContext().getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
        }

        // We are not opening browser for API testing. So putted this condition to check null driver
        if (testContext().getDriver() != null) {
            testContext().getDriver().close();
        }
        testContext().reset();
    }
}
