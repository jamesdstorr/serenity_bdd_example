package com.jamesstorr.jokes_service.application.strategy;

import com.jamesstorr.jokes_service.domain.model.Joke;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component("officialJoke")
public class OfficialJokeStrategy implements JokeStrategy {

    private final WebClient webclient;

    public OfficialJokeStrategy(WebClient officialJokeWebClient) {
        this.webclient = officialJokeWebClient;
    }

    // Inner class for response mapping
    @Data
    @AllArgsConstructor
    private static class OfficialJokeResponse {
        private String setup;
        private String punchline;
    }

    @Override
    public Joke getJoke() {
        return webclient.get()
                    .uri("/jokes/random")
                    .retrieve()
                    .bodyToMono(OfficialJokeResponse.class)
                    .map(this::mapToJoke)
                    .block();
    }

    private Joke mapToJoke(OfficialJokeResponse response) {
        return new Joke(
                response.getSetup(),
                response.getPunchline(),
                "OfficialJokeAPI"
        );
    }
}
