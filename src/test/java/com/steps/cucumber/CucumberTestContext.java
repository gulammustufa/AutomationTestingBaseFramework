package com.steps.cucumber;

import utility.Constant;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.lang.ThreadLocal.withInitial;

public enum CucumberTestContext {
    CONTEXT;
    private static final String RESPONSE = "RESPONSE";
    private static final String SCENARIO = "SCENARIO";
    private static final String GRAPHQL_RESPONSE = "GRAPHQL_RESPONSE";
    private final ThreadLocal<Map<String, Object>> threadLocal = withInitial(HashMap::new);
    private Map<String, Object> testContextMap() {
        return threadLocal.get();
    }
    public void set(String key, Object value) {
        testContextMap().put(key, value);
    }

    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(testContextMap().get(key));
    }

    public void openBrowser() {
        WebDriver driver;
        if (Constant.browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(getChromeOptions());
        } else if (Constant.browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(getChromeOptions());
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        set("DRIVER", driver);
        getScenarioLogger().log(Constant.browserName + " browser is opened.");
        driver.get(Constant.frontBaseUrl); // Go to front end base url
    }

    public void setScenarioLogger(Scenario scenario) {
        set(SCENARIO, scenario);
    }

    public Scenario getScenarioLogger() {
        return get(SCENARIO, Scenario.class);
    }

    public WebDriver getDriver() {
        return (WebDriver) testContextMap().get("DRIVER");
    }

    public void reset() {
        testContextMap().clear();
    }

    public ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--no-sandbox",
                "--disable-logging",
                "--log-level=3.",
                "--remote-allow-origins=*");
        return options;
    }

    public void setResponse(Response response) {
        set(RESPONSE, response);
    }

    public Response getResponse() {
        return get(RESPONSE, Response.class);
    }

    public void setGraphQlResponse(Response response) {
        set(GRAPHQL_RESPONSE, response);
    }

    public Response getGraphQlResponse() {
        return get(GRAPHQL_RESPONSE, Response.class);
    }
}