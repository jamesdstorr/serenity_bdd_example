package com.jamesstorr.jokes_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Joke {
    private String setup;
    private String punchline;
    private String provider;
}
