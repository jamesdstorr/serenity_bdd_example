package com.jamesstorr.jokes_service.acceptancetests;

import com.jamesstorr.jokes_service.actions.JokeApiActions;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@ExtendWith(SerenityJUnit5Extension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WhenRequestingAJoke {

    JokeApiActions actions;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        String baseUrl = "http://localhost:" + port;
        System.out.println(baseUrl);
        actions = new JokeApiActions(baseUrl);
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
}
