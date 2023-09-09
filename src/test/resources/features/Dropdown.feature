@driver @dropdown
Feature: Dropdown feature
  I want to test dropdown feature

  Scenario: Test select dropdown feature
    When User goes to dropdown page
    And User selects option as "Option 1" from dropdown
    Then Selected option should be "Option 1"


  Scenario: Test select dropdown feature with object
    When User goes to dropdown page
    And Dropdown: User selects option as "Option 1" from dropdown
    Then Dropdown: Selected option should be "Option 1"