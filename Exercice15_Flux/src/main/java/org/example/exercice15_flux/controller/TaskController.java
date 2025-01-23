package org.example.exercice15_flux.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController {

    @GetMapping(path = "/api/tasks/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getTask() {
        AtomicInteger counter = new AtomicInteger();

        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "Task " + counter.incrementAndGet());
    }
}
