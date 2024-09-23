package com.jamesstorr.jokes_service.acceptancetests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.jamesstorr.bdd.config.WiremockConfig;
import com.jamesstorr.bdd.stubs.JokeApiStubs;
import com.jamesstorr.jokes_service.actions.JokeApiActions;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@ExtendWith(SerenityJUnit5Extension.class)
@Import(WiremockConfig.class)
public class WhenRequestingAJoke {

    JokeApiActions actions;

    @LocalServerPort
    private int port;

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup(){
        actions = new JokeApiActions();
        JokeApiStubs jokeApiStubs = new JokeApiStubs(wireMockServer);
        // Setup all stubs via the JokeApiStubs class
        jokeApiStubs.setupAllStubs();
    }

    @Test
    @DisplayName("Should be able to retrieve a joke with no provider")
    public void requesting_a_joke() {
        //Given no Provider
        actions.givenNoJokeProvider();
        //When I Request a Joke
        actions.whenIRequestAJoke();
        //Then I should get a success response back
        actions.thenIShouldReceiveARandomJoke();
    }

    @Test
    @DisplayName("Should be able to retrieve a joke with a provider")
    public void requesting_a_joke_with_provider() {
        //Given a request made using the officialJoke Provider
        actions.givenJokeProvider("officialJoke");
        //When I Request a Joke
        actions.whenIRequestAJoke();
        //Then I should get a success response back
        actions.thenIShouldReceiveARandomJoke();
    }

    @Test
    @DisplayName("Should be able to retrieve a joke with the Chuck Norris provider")
    public void requesting_a_joke_with_chucknorris_provider() {
        //Given a request made using the officialJoke Provider
        actions.givenJokeProvider("chuckNorrisJoke");
        //When I Request a Joke
        actions.whenIRequestAJoke();
        //Then I should get a success response back
        actions.thenIShouldReceiveAChuckNorrisJoke();
    }

    @Test
    @DisplayName("Should be able to retrieve a joke with the JokesAPI provider")
    public void requesting_a_joke_with_jokesapi_provider() {
        //Given a request made using the officialJoke Provider
        actions.givenJokeProvider("jokeAPI");
        //When I Request a Joke
        actions.whenIRequestAJoke();
        //Then I should get a success response back
        actions.thenIShouldReceiveACJokesAPIJoke();
    }
}
