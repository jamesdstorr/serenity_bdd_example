package com.jamesstorr.jokes_service.application.strategy;

import com.jamesstorr.jokes_service.domain.model.Joke;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component("jokeAPI")
public class JokeAPIStrategy implements JokeStrategy {

    private final WebClient webClient;

    public JokeAPIStrategy(WebClient jokeApiWebClient) {
        this.webClient = jokeApiWebClient;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class JokeAPIResponse {
        private String type;
        private String joke;
        private String setup;
    }

    @Override
    public Joke getJoke() {
        return webClient.get()
                .uri("/joke/any")
                .retrieve()
                .bodyToMono(JokeAPIResponse.class)
                .map(this::mapToJoke)
                .block();
    }

    private Joke mapToJoke(JokeAPIResponse jokeAPIResponse) {
        if (jokeAPIResponse.type.equals("single")) {
            return Joke.builder()
                    .punchline(jokeAPIResponse.getJoke())
                    .provider("JokeAPI.dev")
                    .build();
        }
        return Joke.builder()
                .setup(jokeAPIResponse.getSetup())
                .punchline(jokeAPIResponse.getJoke())
                .provider("JokeAPI.dev")
                .build();
    }
}
