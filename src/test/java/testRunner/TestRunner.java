package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.steps"},
        monochrome = true,
        plugin = {
                "pretty", "html:target/cucumber-pretty.html",
                "json:target/cucumber.json"
        },
        tags = ""
)

public class TestRunner {

}
