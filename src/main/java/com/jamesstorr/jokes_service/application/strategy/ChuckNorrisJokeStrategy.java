package com.jamesstorr.jokes_service.application.strategy;

import com.jamesstorr.jokes_service.domain.model.Joke;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component("chuckNorrisJoke")
public class ChuckNorrisJokeStrategy implements JokeStrategy {

    private final WebClient webClient;

    @Autowired
    public ChuckNorrisJokeStrategy(WebClient chuckNorrisJokeWebClient) {
        this.webClient = chuckNorrisJokeWebClient;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ChuckNorrisJokesResponse {
        private String value;
    }

    @Override
    public Joke getJoke() {

            return webClient.get()
                    .uri("/jokes/random")
                    .retrieve()
                    .bodyToMono(ChuckNorrisJokesResponse.class)
                    .map(this::mapToJoke)
                    .block();

    }

    private Joke mapToJoke(ChuckNorrisJokesResponse response){
        return Joke.builder()
                .punchline(response.getValue())
                .provider("api.chucknorris.io")
                .build();
    }


}
