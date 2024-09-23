Feature: Retrieve a joke
  As a user
  I want to retrieve a random joke
  So that I can enjoy some humor

  Scenario: Retrieve a joke with a provider
    Given a joke provider "officialJoke"
    When I request a joke
    Then I should receive a random joke with provider "officialJoke"
