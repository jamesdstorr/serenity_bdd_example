package com.jamesstorr.jokes_service.application.service;

import com.jamesstorr.jokes_service.application.exception.ProviderNotFoundException;
import com.jamesstorr.jokes_service.application.strategy.JokeStrategy;
import com.jamesstorr.jokes_service.domain.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JokeServiceImp implements JokeService {

    private final Map<String, JokeStrategy> strategies;

    @Autowired
    public JokeServiceImp(final Map<String, JokeStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public Joke getJoke(String provider) {
        JokeStrategy strategy = strategies.get(provider);
        if(strategy == null) throw new ProviderNotFoundException(provider);
        return strategy.getJoke();
    }

    @Override
    public Joke getJoke() {
       String defaultProvider = "officialJoke";
       JokeStrategy strategy = strategies.get(defaultProvider);
       if(strategy == null) throw new ProviderNotFoundException(defaultProvider);
       return strategy.getJoke();
    }
}
