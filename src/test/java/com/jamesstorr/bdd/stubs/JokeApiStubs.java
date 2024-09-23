package com.jamesstorr.bdd.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class JokeApiStubs {

    private final WireMockServer wireMockServer;

    public JokeApiStubs(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
    }

    public void setupOfficialJokeStub() {
        wireMockServer.stubFor(get(urlEqualTo("/officialJoke/jokes/random"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"setup\": \"Why did the chicken cross the road?\", \"punchline\": \"To get to the other side!\" }")));
    }

    public void setupChuckNorrisStub() {
        wireMockServer.stubFor(get(urlEqualTo("/chuckNorris/jokes/random"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"value\": \"Chuck Norris can divide by zero\" }")));
    }

    public void setupJokeApiStub() {
        wireMockServer.stubFor(get(urlEqualTo("/jokeAPI/joke/any"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"type\": \"single\", \"joke\": \"This is a joke from JokeAPI\" }")));
    }

    public void setupAllStubs() {
        setupOfficialJokeStub();
        setupChuckNorrisStub();
        setupJokeApiStub();
    }
}

