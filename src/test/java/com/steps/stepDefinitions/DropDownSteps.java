package com.steps.stepDefinitions;

import com.steps.cucumber.AbstractSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.DropDownPage;


public class DropDownSteps extends AbstractSteps {
    private final WebDriver driver = testContext().getDriver();
    private final By dropdown = By.id("dropdown");

    DropDownPage dropDownPage = new DropDownPage();

    @When("User selects option as {string} from dropdown")
    public void user_selects_option_as_from_dropdown(String option) {
        Select dropdownButton = new Select(driver.findElement(dropdown));
        dropdownButton.selectByVisibleText(option);
    }

    @Then("Selected option should be {string}")
    public void selected_option_should_be(String expectedOption) {
        Select select = new Select(driver.findElement(dropdown));
        WebElement option = select.getFirstSelectedOption();
        String selectedOption = option.getText();
        Assert.assertEquals(expectedOption, selectedOption);
    }

    @And("Dropdown: User selects option as {string} from dropdown")
    public void dropdownUserSelectsOptionAsFromDropdown(String option) {
        dropDownPage.selectFromDropDown(option);
    }

    @Then("Dropdown: Selected option should be {string}")
    public void dropdownSelectedOptionShouldBe(String option) {
        Assert.assertEquals(option, dropDownPage.getSelectedOption());
    }
}
