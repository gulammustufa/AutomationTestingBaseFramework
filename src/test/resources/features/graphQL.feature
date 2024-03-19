@graphQL
Feature: GraphQL Test

  Scenario Outline: Verify that search by country code gives correct result
    When User calls graphQL country api with country code "<countryCode>" and currency "<currency>"
    Then Verify that response returns data with passed country code "<countryCode>" and currency "<currency>"
    Examples:
      | countryCode | currency    |
      | IN          | INR         |
      | US          | USD,USN,USS |


  Scenario Outline: Verify that search by language code gives correct result
    When User call GraphQL language api with language code "<languageCode>"
    Then Verify that return languages contain only "<languageCode>"
    Examples:
      | languageCode |
      | hi           |