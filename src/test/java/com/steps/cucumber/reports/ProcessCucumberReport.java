package com.steps.cucumber.reports;

import java.nio.file.*;

public class ProcessCucumberReport {

    public static void main(String[] args) throws Exception {
        Path reportDirectory =Paths.get("target/cucumber-html-reports");
        if (!Files.exists(reportDirectory)) {
            System.out.println("Report directory not found: "+ reportDirectory
            );
            return;
        }

        try (var files = Files.walk(reportDirectory)) {
            files.filter(path ->path.toString().endsWith(".html"))
                    .forEach(path -> {
                        try {
                            CucumberReportPostProcessor.process(path);
                            System.out.println("Processed: " + path);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to process: " + path,e);
                        }
                    });
        }
    }
}