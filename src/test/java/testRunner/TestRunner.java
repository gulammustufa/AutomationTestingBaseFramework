package testRunner;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.steps")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.steps")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-report/cucumber.html, json:target/cucumber.json,rerun:target/failed_scenarios.txt"
)

public class TestRunner {

}
