Feature: Fetching Jokes
  As a user
  I want to fetch jokes from different providers
  So that I can enjoy various types of humour

  Background:
    Given the external APIs are stubbed

  Scenario: Fetch a joke using the default provider (OfficialJokeAPI)
    When I request a joke without specifying a provider
    Then I should receive a joke with setup "Why did the chicken cross the road?"
    And the punchline should be "To get to the other side!"
    And the provider should be "OfficialJokeAPI"