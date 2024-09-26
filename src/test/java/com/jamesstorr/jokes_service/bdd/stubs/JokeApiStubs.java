package com.jamesstorr.jokes_service.bdd.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.jamesstorr.jokes_service.bdd.config.WiremockConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Import(WiremockConfig.class)
public class JokeApiStubs {

    @Autowired
    private  WireMockServer wireMockServer;

    public JokeApiStubs(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
        setupAllStubs();
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

