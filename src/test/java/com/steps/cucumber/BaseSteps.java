package com.steps.cucumber;

public class BaseSteps {
    private final CucumberTestContext CONTEXT = CucumberTestContext.CONTEXT;
    protected CucumberTestContext testContext() {
        return CONTEXT;
    }
}
