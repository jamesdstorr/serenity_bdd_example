Feature: Jokes Service

  Scenario: Retrieve a joke without specifying a provider
    Given I do not specify a joke provider
    When I request a joke
    Then I should receive a joke from the default provider

  Scenario: Retrieve a joke from a specific provider
    Given I specify the joke provider "jokeAPI"
    When I request a joke
    Then I should receive a joke from "jokeAPI"