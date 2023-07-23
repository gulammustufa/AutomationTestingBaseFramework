package pages;

import com.steps.cucumber.AbstractSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DropDownPage extends AbstractSteps {
    private final WebDriver driver = testContext().getDriver();
    private final By dropdown = By.id("dropdown");

    public void selectFromDropDown(String option) {
        Select dropdownButton = new Select(driver.findElement(dropdown));
        dropdownButton.selectByVisibleText(option);
    }

    public String getSelectedOption() {
        Select select = new Select(driver.findElement(dropdown));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }
}
