@driver @google
Feature: Google Test
  Scenario: To verify Google page title
    When User go to url "https://www.google.com"
    Then Verify webpage title is "Google"