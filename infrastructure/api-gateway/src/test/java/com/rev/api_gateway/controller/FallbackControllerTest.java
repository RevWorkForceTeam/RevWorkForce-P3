package com.rev.api_gateway.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(FallbackController.class)
class FallbackControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void userServiceFallback_ReturnsMessage() {
        webTestClient.get().uri("/fallback/user")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("User Service is temporarily unavailable. Please try again later.");
    }

    @Test
    void notificationServiceFallback_ReturnsMessage() {
        webTestClient.get().uri("/fallback/notification")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Notification Service is temporarily unavailable.");
    }
}
