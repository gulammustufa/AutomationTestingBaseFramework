package pages;

import com.microsoft.playwright.Page;
import com.steps.cucumber.BaseSteps;

public class DropDownPage extends BaseSteps {
    Page page = testContext().getBrowserPage();
    String dropdownLocator = "#dropdown";

    public void selectFromDropDown(String option) {
        page.locator(dropdownLocator).selectOption(option);
    }

    public String getSelectedOption() {
        return page.locator(dropdownLocator).locator("option:checked").textContent();
    }
}
