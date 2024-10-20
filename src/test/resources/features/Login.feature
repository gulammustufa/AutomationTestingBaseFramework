@driver @login
Feature: Login Test Cases
  I want to test Login functionality

#  This will fail
  Scenario: Login with valid credentials
    When User goes to login page
    And User enters email address as "tomsmithh" and password as "SuperSecretPassword!"
    And Click on login button
    Then Message should be "You logged into a secure area!"


  Scenario Outline: Login with invalid credentials
    When User goes to login page
    And User enters email address as "<email>" and password as "<password>"
    And Click on login button
    Then Validation message should be "Your username is invalid!"
    Examples:
      | email         | password       |
      | WrongUserName | WrongPassword! |