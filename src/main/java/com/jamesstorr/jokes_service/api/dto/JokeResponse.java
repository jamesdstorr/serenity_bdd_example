package com.jamesstorr.jokes_service.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JokeResponse {
    private String setup;
    private String punchline;
    private String provider;
}
