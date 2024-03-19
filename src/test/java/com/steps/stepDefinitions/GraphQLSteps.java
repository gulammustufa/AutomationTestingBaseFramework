package com.steps.stepDefinitions;

import com.jayway.jsonpath.JsonPath;
import com.steps.cucumber.AbstractSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utility.graphQL.GraphQLCommonSteps;
import utility.graphQL.GraphQLEndpoints;
import utility.graphQL.QueryVariables;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GraphQLSteps extends AbstractSteps {
    GraphQLCommonSteps graphQLCommonSteps = new GraphQLCommonSteps();
    @When("User calls graphQL country api with country code {string}")
    public void userCallsGraphQLCountryApiWithCountryCode(String countryCode) {
        graphQLCommonSteps.sendGraphQLQuery(GraphQLEndpoints.COUNTRIES, QueryVariables.builder().countryCode(countryCode).build());
      }

    @Then("Verify that response returns only passed country code {string} data")
    public void verifyThatResponseReturnsOnlyPassedCountryCodeData(String countryCode) {
        var actualCountry = JsonPath.<List<String>>read(testContext().getGraphQlResponse().asString(), "$.data.countries[*].code");
        Assert.assertEquals(countryCode, actualCountry.get(0));
    }

    @When("User calls graphQL country api with country code {string} and currency {string}")
    public void userCallsGraphQLCountryApiWithCountryCodeAndCurrency(String countryCode, String currency) {
        graphQLCommonSteps.sendGraphQLQuery(GraphQLEndpoints.COUNTRIES, QueryVariables.builder().countryCode(countryCode).currency((currency)).build());
    }

    @Then("Verify that response returns data with passed country code {string} and currency {string}")
    public void verifyThatResponseReturnsDataWithPassedCountryCodeAndCurrency(String countryCode, String currency) {
        var actualCountry = JsonPath.<List<String>>read(testContext().getGraphQlResponse().asString(), "$.data.countries[*].code");
        Assert.assertEquals(countryCode, actualCountry.get(0));
        assertThat(actualCountry).contains(countryCode);

        var actualCurrency = testContext().getGraphQlResponse().jsonPath().getString("data.countries[0].currency");
        assertThat(actualCurrency).isEqualTo(currency);
    }

    @When("User call GraphQL language api with language code {string}")
    public void userCallGraphQLLanguageApiWithLanguageCode(String languageCode) {
        graphQLCommonSteps.sendGraphQLQuery(GraphQLEndpoints.LANGUAGES, QueryVariables.builder().languageCode(languageCode).build());
    }

    @Then("Verify that return languages contain only {string}")
    public void verifyThatReturnLanguagesContainOnly(String languageCode) {
        var actualLanguageCode = testContext().getGraphQlResponse().jsonPath().getString("data.languages[0].code");
        assertThat(actualLanguageCode).isEqualTo(languageCode);
    }
}
