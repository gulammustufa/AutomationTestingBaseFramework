@api
Feature: Test cases for IpInfo Api
  Scenario: User call ipInfo Api
    When User sends IpInfo Api
    Then User should get Ip info of the user's Ip