@api
Feature: Test All APIs

  Scenario: All users list should be received after calling getAllUsers api (GET Method)
    When User call get all user api
    Then User should receive list of all the users

  Scenario Outline: User should get info of user after calling single user api (GET Method)
    When User call get user api with user id "<userId>"
    Then User should receive info of user id "<userId>"
    Examples:
      | userId |
      | 1      |

  Scenario Outline: User should be created after calling create user api (POST Method)
    When User call create user api with "<email>" and "<password>"
    Then User should be receive created user id and "<email>"
    Examples:
      | email              | password |
      | eve.holt@reqres.in | pistol   |
