@driver @yahoo
Feature: Yahoo Test
  Scenario: To verify Yahoo page title
    When User go to url "https://www.yahoo.com"
    Then Verify webpage title is "Yahoo | Mail, Weather, Search, Politics, News, Finance, Sports & Videos"