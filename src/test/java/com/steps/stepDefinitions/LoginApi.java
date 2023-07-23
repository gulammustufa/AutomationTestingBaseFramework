package com.steps.stepDefinitions;

import com.jayway.jsonpath.JsonPath;
import com.steps.cucumber.AbstractSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utility.ApiBody;
import utility.ApiUtility;
import utility.Constant;

public class LoginApi extends AbstractSteps {
    private final ApiUtility apiUtility = new ApiUtility();

    @When("User call get all user api")
    public void userCallGetAllUserApi() {
        apiUtility.callGetApi(Constant.API_BASE_URL + "/users?page=1");
    }

    @Then("User should receive list of all the users")
    public void userShouldReceiveListOfAllTheUsers() {
        String perPageData = testContext().getResponse().jsonPath().getString("per_page");
        int dataArraySize = JsonPath.<Integer>read(testContext().getResponse().asString(), "$.data.length()");
        Assert.assertEquals(perPageData, String.valueOf(dataArraySize));
    }

    @When("User call create user api with {string} and {string}")
    public void userCallCreateUserApi(String email, String password) {
        String queryBody = ApiBody.getCreateUserBody(email, password);
        apiUtility.callPostApiWithoutAuthToken(Constant.API_BASE_URL + "/api/register", queryBody);
    }

    @Then("User should be receive created user id and {string}")
    public void userShouldBeReceiveCreatedUserIdAndToken(String expectedEmail) {
        String createdUserEmail = testContext().getResponse().jsonPath().getString("email");
        Assert.assertEquals("Email not matched", expectedEmail, createdUserEmail);

        String createdUserId = testContext().getResponse().jsonPath().getString("id");
        Assert.assertFalse("User id is null", createdUserId.isEmpty());
    }

    @When("User call get user api with user id {string}")
    public void userCallGetUserApiWithUserId(String userId) {
        apiUtility.callGetApi(Constant.API_BASE_URL + "/users/" + userId);
    }

    @Then("User should receive info of user id {string}")
    public void userShouldReceiveInfoOfSingleUser(String expectedUserId) {
        int actualUserId = JsonPath.<Integer>read(testContext().getResponse().asString(), "$.data.id");
        Assert.assertEquals("User id not matched", expectedUserId, String.valueOf(actualUserId));
    }
}
