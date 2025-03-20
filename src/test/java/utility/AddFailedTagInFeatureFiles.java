package utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AddFailedTagInFeatureFiles {
    public static void main(String[] args) throws IOException {
        Path rerunFilePath = Path.of("target/failed_scenarios.txt");
        Path logFilePath = Path.of("target/AddFailedScenariosLogs.txt");

        List<String> logs = new ArrayList<>();

        // Read the failed scenarios file
        List<String> failedScenarios = Files.readAllLines(rerunFilePath).stream()
                .filter(line -> line.contains(":")) // Ensure it's a feature file reference
                .toList();

        if (failedScenarios.isEmpty()) {
            System.out.println("No failed scenarios found. Exiting.");
            return;
        }

        for (String failedScenario : failedScenarios) {
            logs.add("======================");
            String featureFileName = failedScenario.split("/")[1].split(":")[0];

            logs.add("Feature File Name: " + featureFileName);
            String featureFilePath = "src/test/resources/features/" + featureFileName;
            // Extract the failed line number
            String[] parts = failedScenario.split("feature:");
            int failedLineIndex = Integer.parseInt(parts[1].trim()) - 1;

            Path failedFeatureFilePath = Path.of(featureFilePath);
            List<String> featureLines = Files.readAllLines(failedFeatureFilePath);
            HashMap<Integer, String> updatedLines = new HashMap<>();

            for (int k = 0; k < featureLines.size(); k++) {
                updatedLines.put(k, featureLines.get(k));
            }

            String failedLineText = updatedLines.get(failedLineIndex);
            // Check if its Scenarios Outline
            if (failedLineText.contains("|")) {
                logs.add("Found Scenario Outline");
                // As this is scenario example need to add comment for passed example
                // For next example
                for (int i = failedLineIndex + 1; i < updatedLines.size(); i++) {
                    String nextLineText = updatedLines.get(i);
                    if (nextLineText.contains("|")) {
                        if (!updatedLines.get(i).contains("#")) {
                            logs.add("Commenting line number : " + (i + 1));
                            updatedLines.put(i, "#" + nextLineText);
                        } else {
                            logs.add("Already commented line number : " + (i + 1));
                        }
                    } else {
                        break;
                    }
                }

                // For previous example
                int scenarioTagLineIndex = 0;
                for (int i = failedLineIndex - 1; i > 0; i--) {
                    String previousLineText = updatedLines.get(i);
                    if (previousLineText.contains("|") && !updatedLines.get(i - 1).contains("Examples")) {
                        if (!updatedLines.get(i).contains("#")) {
                            logs.add("Commenting line number : " + (i + 1));
                            updatedLines.put(i, "#" + previousLineText);
                        } else {
                            logs.add("Already commented line number : " + (i + 1));
                        }
                    } else if (previousLineText.contains("Scenario")) {
                        scenarioTagLineIndex = i - 1;
                    } else if (scenarioTagLineIndex > 0) {
                        break;
                    }
                }

                if (!updatedLines.get(scenarioTagLineIndex).contains("@failed")) {
                    logs.add("Adding failed tag on line number: " + (scenarioTagLineIndex + 1));
                    updatedLines.put(scenarioTagLineIndex, updatedLines.get(scenarioTagLineIndex) + " @failed");
                } else {
                    logs.add("Failed tag already on line number: " + (scenarioTagLineIndex));
                }

            }

            // Check if its Normal Scenario
            else if (failedLineText.contains("Scenario")) {
                if (!updatedLines.get(failedLineIndex - 1).contains("@failed")) {
                    logs.add("Found only Scenario");
                    logs.add("Adding failed tag on line number: " + (failedLineIndex));
                    updatedLines.put(failedLineIndex - 1, updatedLines.get(failedLineIndex - 1) + " @failed");
                } else {
                    logs.add("Failed tag already on line number: " + (failedLineIndex));
                }

            }
            Collection<String> lines = updatedLines.values();
            try {
                Files.write(failedFeatureFilePath, lines); // Write the lines to the file
                logs.add("File written successfully: " + failedFeatureFilePath.toAbsolutePath());
            } catch (IOException e) {
                logs.add("Error writing to file: " + e.getMessage());
            }
        }

        Files.write(logFilePath, logs);
        System.out.println("Feature file updated successfully with @failed tag.");
        System.out.println("Check logs in " + logFilePath.toAbsolutePath());
    }
}
