package com.steps.stepDefinitions;

import com.steps.cucumber.AbstractSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utility.ApiUtility;
import utility.Constant;

public class IpInfoApi extends AbstractSteps {
    private final ApiUtility apiUtility = new ApiUtility();
    @When("User sends IpInfo Api")
    public void userSendsIpInfoApi() {
        apiUtility.callPostApiWithAuthToken(Constant.ipInfoUrl, Constant.ipInfoToken);
    }

    @Then("User should get Ip info of the user's Ip")
    public void userShouldGetIpInfoOfTheUserSIp() {
        String userIp = testContext().getResponse().jsonPath().getString("ip");
        Assert.assertFalse(userIp.isEmpty());
    }
}
