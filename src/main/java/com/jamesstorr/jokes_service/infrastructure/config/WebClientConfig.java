package com.jamesstorr.jokes_service.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient officialJokeWebClient(@Value("${external.officialjokeapi.base-url}") String baseUrl){
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public WebClient chuckNorrisJokeWebClient(@Value("${external.chucknorrisapi.base-url}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public WebClient jokeApiWebClient(@Value("${external.jokeapi.base-url}") String baseUrl) {
        System.out.printf("Using Base URL: %s", baseUrl);
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        System.out.println(client);
        return client;
    }
}
