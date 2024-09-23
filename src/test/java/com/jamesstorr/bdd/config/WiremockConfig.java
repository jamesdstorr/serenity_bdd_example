package com.jamesstorr.bdd.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@TestConfiguration
public class WiremockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Scope("singleton") // Ensure a single instance is used across tests
    public WireMockServer wireMockServer() {
        return new WireMockServer(options().port(9090));
    }
}
