package org.example.exercice15_flux;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.exercice15_flux.controller.TaskController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@WebFluxTest(controllers = TaskController.class)
public class TaskControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void getTaskTest(){
        webClient.get()
                .uri("/api/tasks/stream")
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody()
                .as(StepVerifier::create)
                .expectNext("Task 1")
                .expectNext("Task 2")
                .expectNext("Task 3")
                .thenCancel()
                .verify();
    }
}
