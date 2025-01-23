package org.example.exercice14_flux;

import org.example.exercice14_flux.controller.OrderController;
import org.example.exercice14_flux.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(OrderController.class)
public class OrderTestController {
    @Autowired
    private WebTestClient client;

    @Test
    public void testGetOrders() {
        client.get()
                .uri("/api/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].item").isEqualTo("Laptop")
                .jsonPath("$[1].id").isEqualTo(2)
                .jsonPath("$[1].item").isEqualTo("Phone");
    }

    @Test
    public void addCommand() {

        Order newOrder = new Order(3, "Tablet");

        client.post()
                .uri("/api/orders")
                .contentType(APPLICATION_JSON)
                .body(Mono.just(newOrder), Order.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(3)
                .jsonPath("$.item").isEqualTo("Tablet");

        client.get()
                .uri("/api/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[2].id").isEqualTo(3)
                .jsonPath("$[2].item").isEqualTo("Tablet");
    }
}
