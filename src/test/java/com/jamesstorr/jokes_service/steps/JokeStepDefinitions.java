package com.jamesstorr.jokes_service.steps;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class JokeStepDefinitions {


    private String provider;
    private String baseUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private WireMockServer wireMockServer;

    @Before
    public void setup() {
        this.baseUrl = "http://localhost:" + port;
        System.out.println(baseUrl);

        // Set up WireMock stubs
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

    @Given("I do not specify a joke provider")
    public void stepIDoNotSpecifyAJokeProvider() {
        this.provider = null;
    }

    @Given("I specify the joke provider {string}")
    public void stepISpecifyTheJokeProvider(String provider) {
        this.provider = provider;
    }

    @When("I request a joke")
    public void stepIRequestAJoke() {
        String endpoint = baseUrl + "/api/jokes";
        Response response;
        if (provider != null) {
            response = SerenityRest.given()
                    .queryParam("provider", provider)
                    .when()
                    .get(endpoint)
                    .then()
                    .extract().response();
        } else {
            response = SerenityRest.given()
                    .when()
                    .get(endpoint)
                    .then()
                    .extract().response();
        }
    }

    @Then("I should receive a joke from the default provider")
    public void stepIShouldReceiveAJokeFromTheDefaultProvider() {
        SerenityRest.then()
                .body("setup", equalTo("Why did the chicken cross the road?"))
                .body("punchline", equalTo("To get to the other side!"))
                .body("provider", notNullValue());
    }

    @Then("I should receive a joke from {string}")
    public void stepIShouldReceiveAJokeFrom(String provider) {
        SerenityRest.then()
                .body("punchline", equalTo("Chuck Norris can divide by zero"));
    }
}
