package com.jamesstorr.jokes_service.config;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {WireMockConfig.class}
)
@ActiveProfiles("test")
public class CucumberSpringConfig {
}
