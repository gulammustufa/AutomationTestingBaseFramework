package utility.graphQL;

import com.steps.cucumber.AbstractSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utility.Constant;

import static io.restassured.RestAssured.given;

public class GraphQLCommonSteps extends AbstractSteps {
    public void sendGraphQLQuery(GraphQLEndpoints graphQLEndpoints, QueryVariables variables) {
        GraphQLQueryBuilder graphQLQueryBuilder = new GraphQLQueryBuilder();
        graphQLQueryBuilder.setQuery(setQueryTemplate(graphQLEndpoints));
        graphQLQueryBuilder.setVariables(variables);
        testContext().getScenarioLogger().log("GraphQL Request: " + graphQLQueryBuilder);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(graphQLQueryBuilder).post(Constant.graphQlApiUrl);
        testContext().getScenarioLogger().log("GraphQL Response: " + response.asString());
        testContext().setGraphQlResponse(response);
    }

    private String setQueryTemplate(GraphQLEndpoints endpoint) {
        return switch (endpoint) {
            case COUNTRIES -> GraphQLQueryBodyTemplates.countriesQueryBody();
            case LANGUAGES -> GraphQLQueryBodyTemplates.languagesQueryBody();
        };
    }
}
