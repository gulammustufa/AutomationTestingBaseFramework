package com.steps.cucumber;

import com.microsoft.playwright.Page;
import io.cucumber.java.*;
import org.assertj.core.api.SoftAssertions;
import utility.Constant;
import utility.DateTimeUtility;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Hooks extends BaseSteps {
    private static String starTimeString;
    private static final String ddMmmYyyyDateFormat = "dd MMM yyyy, hh:mm:ss a";
    private static final String FILE_PATH = "src/test/resources/report.properties";
    static String currentTestEnv = Constant.getTestEnv();

    @BeforeAll()
    public static void beforeAll() {
        starTimeString = DateTimeUtility.getCurrentDateTimeInFormat(ddMmmYyyyDateFormat);
        writeProperty("Start_Time", starTimeString);
        writeProperty("Test_Env", currentTestEnv);
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) throws IOException {
        testContext().setScenarioLogger(scenario);
        testContext().getScenarioLogger().log("SETUP SCENARIO: " + scenario.getName());
        Constant.setUpTestEnvData();
        testContext().getScenarioLogger().log("testEnv = " + Constant.testEnv);
    }

    @Before(value = "@driver", order = 1)
    public void setUpBrowser() {
        testContext().openBrowser();
    }

    @After()
    public void tearDown(Scenario scenario) throws IOException {
        testContext().getScenarioLogger().log("GENERIC TEARDOWN: " + scenario.getName());
        Page page = testContext().getBrowserPage();
        boolean failed = scenario.isFailed();

        if (page != null && (failed || scenario.getStatus().name().equalsIgnoreCase("SKIPPED"))) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/tmp", "screenshot.png")).setFullPage(true));
            scenario.attach(screenshot, "image/png", "Screenshot");
        }

        SoftAssertions softAssertions = testContext().getSoftAssertion();
        if (softAssertions != null) {
            softAssertions.assertAll();
            StringBuilder assertionErrors = new StringBuilder();
            softAssertions.errorsCollected().forEach(error -> assertionErrors.append(error.toString()).append("\n"));

            // Log or report assertion errors
            if (!assertionErrors.isEmpty()) {
                testContext().getScenarioLogger().log("Soft assertion errors:\n" + assertionErrors);
                // You can also log assertion errors to a logger or any other reporting mechanism
            }
        }

        if (page != null) {
            String scenarioName = scenario.getName()
                    .replaceAll("[^a-zA-Z0-9.-]", "_");
            String fileName = scenarioName
                    + "_" + Thread.currentThread().getId()
                    + "_" + System.currentTimeMillis()
                    + ".webm";
            Path videoPath = Objects.requireNonNull(page.video()).path();
            page.close();
            testContext().getBrowser().close();
            testContext().getPlaywright().close();
            testContext().reset();

            if (failed) {
                Path newVideoPath = Paths.get("target/videos/" + fileName);
                Files.createDirectories(newVideoPath.getParent());
                Files.move(
                        videoPath,
                        newVideoPath,
                        StandardCopyOption.REPLACE_EXISTING
                );

                // Attach video location
                scenario.attach(
                        newVideoPath.toAbsolutePath().toString().getBytes(StandardCharsets.UTF_8),
                        "text/plain",
                        "Video Location"
                );
            } else {
                // Test passed → remove the video
                Files.deleteIfExists(videoPath);
            }
        }
    }

    @AfterAll()
    public static void afterAll() {
        String endTimeString = DateTimeUtility.getCurrentDateTimeInFormat(ddMmmYyyyDateFormat);
        writeProperty("End_Time", endTimeString);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ddMmmYyyyDateFormat);

        LocalDateTime startTime = LocalDateTime.parse(starTimeString, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeString, formatter);
        Duration duration = Duration.between(startTime, endTime);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        String formattedDuration = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        writeProperty("Duration", formattedDuration);
    }

    private static void writeProperty(String key, String value) {
        Map<String, String> orderedProperties = new LinkedHashMap<>();

        // Load existing properties file while maintaining order
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#") && line.contains("=")) {  // Ignore comments
                    String[] parts = line.split("=", 2);
                    orderedProperties.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Properties file not found, creating a new one.");
        }

        // Update or add new key-value
        orderedProperties.put(key, value);

        // Write updated properties back without timestamp or escaping
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, String> entry : orderedProperties.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Exception while updating properties file.");
        }
    }
}
