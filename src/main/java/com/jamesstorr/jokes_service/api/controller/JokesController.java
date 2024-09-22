package com.jamesstorr.jokes_service.api.controller;

import com.jamesstorr.jokes_service.application.service.JokeService;
import com.jamesstorr.jokes_service.domain.model.Joke;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jokes")
public class JokesController {
    private final JokeService jokesService;

    @Autowired
    public JokesController(JokeService jokesService) {
        System.out.println("JokesController instantiated");
        this.jokesService = jokesService;
    }

    @PostConstruct
    public void init() {
        System.out.println("JokesController instantiated");
    }

    @GetMapping
    public Joke getJoke(
            @RequestParam(required = false) String provider){
        if(provider != null)
            return jokesService.getJoke(provider);
        else {
            return jokesService.getJoke();
        }
    }
}
