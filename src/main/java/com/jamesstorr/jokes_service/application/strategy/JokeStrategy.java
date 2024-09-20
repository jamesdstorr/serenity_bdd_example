package com.jamesstorr.jokes_service.application.strategy;

import com.jamesstorr.jokes_service.domain.model.Joke;

public interface JokeStrategy {
    Joke getJoke();
}
