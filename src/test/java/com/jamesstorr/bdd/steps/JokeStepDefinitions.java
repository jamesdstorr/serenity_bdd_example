package com.jamesstorr.bdd.steps;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.jamesstorr.bdd.config.WiremockConfig;
import com.jamesstorr.bdd.stubs.JokeApiStubs;
import com.jamesstorr.jokes_service.actions.JokeApiActions;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(WiremockConfig.class)
public class JokeStepDefinitions {

    @Autowired
    private JokeApiActions actions; // Autowired now

    @LocalServerPort
    private int port;

    @Autowired
    private WireMockServer wireMockServer;

    @Before
    public void setup(){
        JokeApiStubs jokeApiStubs = new JokeApiStubs(wireMockServer);
        // Setup all stubs via the JokeApiStubs class
        jokeApiStubs.setupAllStubs();
    }

    @Given("a joke provider {string}")
    public void aJokeProvider(String provider) {
        actions.givenJokeProvider(provider);
    }

    @When("I request a joke")
    public void iRequestAJoke() {
        actions.whenIRequestAJoke();
    }

    @Then("I should receive a random joke with provider {string}")
    public void iShouldReceiveARandomJokeWithProvider(String provider) {
        actions.thenIShouldReceiveAJokeWithProvider(provider);
    }
}
