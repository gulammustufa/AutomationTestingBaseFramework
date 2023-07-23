@driver @login
Feature: Login Test Cases
  I want to test Login functionality

  Scenario: Login with valid credentials Latest
    When User go to url "https://the-internet.herokuapp.com/login"
    And User enters email address as "tomsmith" and password as "SuperSecretPassword!"
    And Click on login button
    Then Page url should be "https://the-internet.herokuapp.com/secure"


  Scenario Outline: Login with invalid credentials Latest
    When User go to url "https://the-internet.herokuapp.com/login"
    And User enters email address as "<email>" and password as "<password>"
    And Click on login button
    Then Page url should be "https://the-internet.herokuapp.com/login"
    Examples:
      | email         | password       |
      | WrongUserName | WrongPassword! |