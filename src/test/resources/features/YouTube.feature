@driver @youtube
Feature: YouTube Test
  Scenario: To verify YouTube page title
    When User go to url "https://www.youtube.com"
    Then Verify webpage title is "YouTube"