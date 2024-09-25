Feature: Joke API Tests
  Scenario: Get a Valid Joke Response
    Given a joke provider "officialJoke"
    When I request a joke
    Then I should receive a random joke with provider "OfficialJokeAPI"

  Scenario: Get a Chuck Norris Joke
    Given a joke provider "chuckNorrisJoke"
    When I request a joke
    Then I should receive a random joke with provider "api.chucknorris.io"
