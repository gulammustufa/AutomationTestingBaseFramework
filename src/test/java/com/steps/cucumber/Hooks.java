package com.steps.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utility.Constant;

import java.io.IOException;

public class Hooks extends AbstractSteps {
    @Before(order = 0)
    public void setUp(Scenario scenario) throws IOException {
        testContext().setScenarioLogger(scenario);
        testContext().getScenarioLogger().log("SETUP SCENARIO " + scenario.getName());
        Constant.setUpTestEnvData();
        testContext().getScenarioLogger().log("testEnv = " + Constant.testEnv);
    }

    @Before(value = "@driver", order = 1)
    public void setUpBrowser() {
        testContext().openBrowser();
    }

    @After()
    public void tearDown(Scenario scenario) {
        testContext().getScenarioLogger().log("GENERIC TEARDOWN " + scenario.getName());

        // We are not opening browser for API testing. So putted this condition to check null driver
        if (testContext().getDriver() != null) {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) testContext().getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            }
            testContext().getDriver().close();
        }
        testContext().reset();
    }
}
