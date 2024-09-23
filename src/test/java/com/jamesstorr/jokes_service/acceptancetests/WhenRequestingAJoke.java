package com.jamesstorr.jokes_service.acceptancetests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.jamesstorr.jokes_service.actions.JokeApiActions;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@ExtendWith(SerenityJUnit5Extension.class)
public class WhenRequestingAJoke {

    JokeApiActions actions;

    @LocalServerPort
    private int port;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(options()
                .port(9090)
                );  // Fixed port 9090
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @BeforeEach
    public void setupStubs() {

        actions = new JokeApiActions(port);

        // Stub for OfficialJokeAPI
        wireMockServer.stubFor(get(urlEqualTo("/officialJoke/jokes/random"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"setup\": \"Why did the chicken cross the road?\", \"punchline\": \"To get to the other side!\" }")));

        // Stub for ChuckNorrisAPI
        wireMockServer.stubFor(get(urlEqualTo("/chuckNorris/jokes/random"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"value\": \"Chuck Norris can divide by zero\" }")));

        // Stub for JokeAPI
        wireMockServer.stubFor(get(urlEqualTo("/jokeAPI/jokes/random"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"punchline\": \"This is a joke from JokeAPI.\" }")));
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
}
