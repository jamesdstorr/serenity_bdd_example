
package com.jamesstorr.jokes_service.bdd.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WiremockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        WireMockServer server = new WireMockServer(options()
                .port(9090)
                .notifier(new ConsoleNotifier(true)) // Enable verbose logging
        );
        server.start();
        System.out.println("WireMockServer started on port 9090.");
        return server;
    }
}

