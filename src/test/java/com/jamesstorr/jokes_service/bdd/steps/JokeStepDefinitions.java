package com.jamesstorr.jokes_service.bdd.steps;

import com.jamesstorr.jokes_service.bdd.actions.JokeApiActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import org.springframework.stereotype.Component;

@Component
public class JokeStepDefinitions {

    @Steps
    private JokeApiActions actions;

    @Given("a joke provider {string}")
    public void aJokeProvider(String provider) {
        actions.givenJokeProvider(provider);
    }

    @When("I request a joke")
    public void iRequestAJoke() {
        actions.whenIRequestAJoke();
    }

    @Then("I should receive a random joke with provider {string}")
    public void iShouldReceiveAJokeWithProvider(String provider) {
        actions.thenIShouldReceiveAJokeWithProvider(provider);
    }
}
