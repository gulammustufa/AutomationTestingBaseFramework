package utility;

import com.steps.cucumber.AbstractSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiUtility extends AbstractSteps {
    public void callGetApi(String getApiUrl) {
        testContext().getScenarioLogger().log("\nGet Api Url:\n" + getApiUrl);
        Response response = given().get(getApiUrl);
        testContext().setResponse(response);
        testContext().getScenarioLogger().log("\nResponse = \n" + response.asPrettyString());
    }

    public void callPostApiWithoutAuthToken(String postApiUrl, String queryBody) {
        testContext().getScenarioLogger().log("\nPost Api Info:\n" + postApiUrl + "\n\nBody:\n" + queryBody);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(queryBody)
                .post(postApiUrl);
        testContext().getScenarioLogger().log("\nResponse = \n" + response.asPrettyString());
        testContext().setResponse(response);
    }
    
    public void callPostApiWithAuthTokenAndBody(String postApiUrl, String queryBody, String token){
        testContext().getScenarioLogger().log("\nPost Api Info:\n" + postApiUrl + "\n\nBody:\n" + queryBody);
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(queryBody)
                .post(postApiUrl);
        testContext().getScenarioLogger().log("\nResponse = \n" + response.asPrettyString());
        testContext().setResponse(response);
    }

    public void callPostApiWithAuthToken(String postApiUrl, String token){
        testContext().getScenarioLogger().log("\nPost Api Info:\n" + postApiUrl);
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .post(postApiUrl);
        testContext().getScenarioLogger().log("\nResponse = \n" + response.asPrettyString());
        testContext().setResponse(response);
    }
}
