@driver @login
Feature: Login Test Cases
  I want to test Login functionality

  Scenario: Login with valid credentials Latest
    When User goes to login page
    And User enters email address as "tomsmith" and password as "SuperSecretPassword!"
    And Click on login button
    Then Message should be "You logged into a secure area!"


  Scenario Outline: Login with invalid credentials Latest
    When User goes to login page
    And User enters email address as "<email>" and password as "<password>"
    And Click on login button
    Then Validation message should be "Your username is invalid!"
    Examples:
      | email         | password       |
      | WrongUserName | WrongPassword! |