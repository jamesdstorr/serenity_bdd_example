package com.jamesstorr.jokes_service.actions;

import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class JokeApiActions {

    private String provider;
    private final String baseUrl;

    public JokeApiActions(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public void givenJokeProvider(String provider) {
        this.provider = provider;
    }

    public void givenNoJokeProvider() {
        this.provider = null;
    }

    public void whenIRequestAJoke() {
        String endpoint = baseUrl + "/api/jokes";
        System.out.println("Calling Endpoint: %s with provider value %s".formatted(endpoint, provider));
        if (provider != null) {
            SerenityRest.given()
                    .queryParam("provider", provider)
                    .when()
                    .get(endpoint)
                    .then()
                    .statusCode(200);
        } else {
            SerenityRest.given()
                    .when()
                    .get(endpoint)
                    .then()
                    .statusCode(200);
        }
    }

    public void thenIShouldReceiveAJokeWithProvider(String expectedProvider) {
        SerenityRest.then()
                .body("provider", equalTo(expectedProvider));
    }

    public void thenIShouldReceiveARandomJoke() {
        SerenityRest.then()
                .body("setup", notNullValue())
                .body("punchline", notNullValue())
                .body("provider", notNullValue());
    }
}