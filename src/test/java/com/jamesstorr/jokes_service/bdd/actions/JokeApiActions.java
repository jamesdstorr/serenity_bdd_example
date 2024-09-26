
package com.jamesstorr.jokes_service.bdd.actions;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.jamesstorr.jokes_service.bdd.config.WiremockConfig;
import com.jamesstorr.jokes_service.bdd.stubs.JokeApiStubs;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class JokeApiActions {

    private String provider;

    @LocalServerPort
    private int port;

    @Autowired
    private JokeApiStubs jokeApiStubs;


    public void givenJokeProvider(String provider) {
        this.provider = provider;
    }

    public void givenNoJokeProvider() {
        this.provider = null;
    }

    public void whenIRequestAJoke() {
        String baseUrl = "http://localhost:" + port;
        String endpoint = baseUrl + "/api/jokes";
        System.out.printf("Calling endpoint: %s%n", endpoint);
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
        System.out.println(response);
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

    public void thenIShouldReceiveACJokesAPIJoke(){
        SerenityRest.then()
                .body("punchline", equalTo("This is a joke from JokeAPI"));
    }
}