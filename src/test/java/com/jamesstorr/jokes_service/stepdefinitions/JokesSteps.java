package com.jamesstorr.jokes_service.stepdefinitions;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.jamesstorr.jokes_service.api.dto.JokeResponse;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {com.jamesstorr.jokes_service.infrastructure.config.WireMockTestConfig.class})
@Component
public class JokesSteps {


    @Autowired
    private WireMockServer wireMockServer;

    private JokeResponse lastJokeResponse;
    private int lastStatusCode;

    public JokesSteps(){}

    @Before
    public void setupStubs(){
        // Stub for Official Joke API (default provider)
        wireMockServer.stubFor(get(urlEqualTo("/jokes/random"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"setup\": \"Why did the chicken cross the road?\", \"punchline\": \"To get to the other side!\" }")));

        // Stub for Chuck Norris API
        wireMockServer.stubFor(get(urlEqualTo("/jokes/random"))
                .withQueryParam("provider", equalTo("chuckNorrisJoke"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"value\": \"Chuck Norris counted to infinity. Twice.\" }")));

        // Stub for JokeAPI.dev
        wireMockServer.stubFor(get(urlPathMatching("/joke/Any.*"))
                .withQueryParam("provider", equalTo("jokeApi"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"type\": \"single\", \"joke\": \"I would tell you a construction joke, but I'm still working on it.\" }")));

        // Stub for Invalid Provider
        wireMockServer.stubFor(get(urlPathEqualTo("/api/jokes"))
                .withQueryParam("provider", equalTo("invalidProvider"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"error\": \"Joke provider not found: invalidProvider\" }")));
    }

    @Given("the external APIs are stubbed")
    public void theExternalAPIsAreStubbed() {
        // Stubs are set up in @Before
    }

    @When("I request a joke without specifying a provider")
    public void i_request_a_joke_without_specifying_a_provider() {
        // Perform GET request to /api/jokes without provider parameter
        lastJokeResponse = SerenityRest.rest()
                .given()
                .when()
                .get("/api/jokes")
                .then()
                .extract()
                .as(JokeResponse.class);

        lastStatusCode = SerenityRest.rest()
                .given()
                .when()
                .get("/api/jokes")
                .then()
                .extract()
                .statusCode();
    }

    @When("I request a joke with provider {string}")
    public void i_request_a_joke_with_provider(String provider) {
        // Perform GET request to /api/jokes with provider parameter
        lastJokeResponse = SerenityRest.rest()
                .given()
                .when()
                .get("/api/jokes?provider=" + provider)
                .then()
                .extract()
                .as(JokeResponse.class);

        lastStatusCode = SerenityRest.rest()
                .given()
                .when()
                .get("/api/jokes?provider=" + provider)
                .then()
                .extract()
                .statusCode();
    }

    @Then("I should receive a joke with setup {string}")
    public void i_should_receive_a_joke_with_setup(String expectedSetup) {
        Assertions.assertThat(lastJokeResponse)
                .isNotNull()
                .extracting(JokeResponse::getSetup)
                .isEqualTo(expectedSetup);
    }

    @Then("the punchline should be {string}")
    public void the_punchline_should_be(String expectedPunchline) {
        if ("null".equalsIgnoreCase(expectedPunchline)) {
            Assertions.assertThat(lastJokeResponse.getPunchline())
                    .isNull();
        } else {
            Assertions.assertThat(lastJokeResponse.getPunchline())
                    .isEqualTo(expectedPunchline);
        }
    }

    @Then("the provider should be {string}")
    public void the_provider_should_be(String expectedProvider) {
        Assertions.assertThat(lastJokeResponse.getProvider())
                .isEqualTo(expectedProvider);
    }




}
