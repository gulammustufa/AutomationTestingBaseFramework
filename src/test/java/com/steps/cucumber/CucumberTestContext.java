package com.steps.cucumber;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Geolocation;
import org.assertj.core.api.SoftAssertions;
import utility.Constant;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static java.lang.ThreadLocal.withInitial;

public enum CucumberTestContext {
    CONTEXT;
    final String currUser = System.getProperty("user.name");
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
        Browser browser;
        Playwright playwright = Playwright.create();
        browser = switch (Constant.browserName) {
            case "msedge" -> playwright.chromium().launch(getLaunchOptions(Constant.browserName));
            case "chrome" ->playwright.chromium().launch(getLaunchOptions("chromium"));
            case "firefox" -> playwright.firefox().launch(getLaunchOptions(Constant.browserName));
            default -> playwright.chromium().launch(getLaunchOptions("chrome"));
        };

        BrowserContext context;
        boolean headlessBrowser = getIsHeadLessBrowser();
        Path recordVideoDir = Paths.get("target/raw_videos/");
        if (!headlessBrowser) {
            // Create a new incognito browser context
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screenSize.getWidth();
            int height = (int) screenSize.getHeight();
            context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height).setRecordVideoDir(recordVideoDir)
                    .setRecordVideoSize(1280, 720));
        } else {
            context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(recordVideoDir)
                    .setRecordVideoSize(1280, 720));
        }

        // Create a new page inside context.
        Geolocation geolocation = new Geolocation(23.0244537, 72.5587647);
        context.setGeolocation(geolocation);
        context.grantPermissions(List.of("geolocation"));
        Page page = context.newPage();
        page.setDefaultNavigationTimeout(120000);

        page.navigate(Constant.frontBaseUrl);

        set("BROWSER", browser);
        set("PLAYWRIGHT", playwright);
        set("PAGE", page);
        getScenarioLogger().log(Constant.browserName + " browser is opened.");
    }

    public void setScenarioLogger(Scenario scenario) {
        set(SCENARIO, scenario);
    }

    public Scenario getScenarioLogger() {
        return get(SCENARIO, Scenario.class);
    }

    public Playwright getPlaywright() {
        return (Playwright) testContextMap().get("PLAYWRIGHT");
    }

    public Browser getBrowser() {
        return (Browser) testContextMap().get("BROWSER");
    }

    public Page getBrowserPage() {
        return (Page) testContextMap().get("PAGE");
    }

    public void reset() {
        testContextMap().clear();
    }

    public BrowserType.LaunchOptions getLaunchOptions(String browserName) {
        String currUser = System.getProperty("user.name");
        if (browserName.equals("chromium") && currUser.contains("jenkins")){
            browserName = "chrome";
        }
        boolean headlessBrowser = getIsHeadLessBrowser();
        return new BrowserType.LaunchOptions()
                .setChannel(browserName)
                .setHeadless(headlessBrowser)
                .setSlowMo(500);
    }

    public boolean getIsHeadLessBrowser() {
        String headLessBrowserFromCommandLine = System.getProperty("headLessBrowser");
        return currUser.contains("jenkins") || (headLessBrowserFromCommandLine != null && headLessBrowserFromCommandLine.equals("true"));
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

    public void setSoftAssertions(SoftAssertions softAssertions) {
        set("SOFT_ASSERTION", softAssertions);
    }

    public SoftAssertions getSoftAssertion() {
        return (SoftAssertions) testContextMap().get("SOFT_ASSERTION");
    }
}