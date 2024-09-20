package com.jamesstorr.jokes_service.application.service;

import com.jamesstorr.jokes_service.domain.model.Joke;

public interface JokeService {
    public Joke getJoke(String provider);
    public Joke getJoke();
}
