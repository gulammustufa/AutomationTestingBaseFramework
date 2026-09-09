@driver @pageTitle
Feature: Page title test cases

  Scenario Outline: Verify page title of the URLs
    When User go to url "<url>"
    Then Verify webpage title is "<expectedTitle>"
    Examples:
      | url                      | expectedTitle |
      | https://www.google.com   | Google        |
      | https://www.facebook.com | Facebook      |
      | https://www.youtube.com  | YouTube       |