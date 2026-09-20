package com.steps.stepDefinitions;

import com.microsoft.playwright.Page;
import com.steps.cucumber.BaseSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.DropDownPage;

import static org.assertj.core.api.Assertions.assertThat;

public class DropDownSteps extends BaseSteps {
    Page  page = testContext().getBrowserPage();
    DropDownPage dropDownPage = new DropDownPage();;
    String dropdownLocator = "#dropdown";

    @When("User selects option as {string} from dropdown")
    public void user_selects_option_as_from_dropdown(String option) {
        page.locator(dropdownLocator).selectOption(option);
    }

    @Then("Selected option should be {string}")
    public void selected_option_should_be(String expectedOption) {
        String selectedOption = page.locator(dropdownLocator)
                .locator("option:checked")
                .textContent();
        assertThat(selectedOption).isEqualTo(expectedOption);
    }

    @And("Dropdown: User selects option as {string} from dropdown")
    public void dropdownUserSelectsOptionAsFromDropdown(String option) {
        dropDownPage.selectFromDropDown(option);
    }

    @Then("Dropdown: Selected option should be {string}")
    public void dropdownSelectedOptionShouldBe(String option) {
        assertThat(dropDownPage.getSelectedOption()).isEqualTo(option);
    }

    @When("User goes to dropdown page")
    public void userGoesToDropdownPage() {
        page.getByText("Dropdown").click();
    }
}
