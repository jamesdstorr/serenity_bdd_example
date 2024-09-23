package com.jamesstorr.jokes_service.actions;

import com.jamesstorr.jokes_service.application.service.JokeService;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Component
public class JokeApiActions {

    private String provider;
    private String baseUrl;

    @MockBean
    private JokeService jokeService;

    // Default constructor required by Serenity
    public JokeApiActions() {
    }

    public JokeApiActions(@Value("${local.server.port}") int port) {
        this.baseUrl = "http://localhost:" + port;
    }

    public void givenJokeProvider(String provider) {
        this.provider = provider;
    }

    public void givenNoJokeProvider() {
        this.provider = null;
    }

    public void whenIRequestAJoke() {
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

    public void thenIShouldReceiveAJokeWithProvider(String expectedProvider) {
        SerenityRest.then()
                .body("provider", equalTo(expectedProvider));
    }

    public void thenIShouldReceiveARandomJoke() {
        SerenityRest.then()
                .body("setup", equalTo("Why did the chicken cross the road?"))
                .body("punchline", equalTo("To get to the other side!"))
                .body("provider", notNullValue());
    }

    public void thenIShouldReceiveAChuckNorrisJoke(){
        SerenityRest.then()
                .body("punchline", equalTo("Chuck Norris can divide by zero"));
    }
}