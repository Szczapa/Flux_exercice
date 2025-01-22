package org.example.exercice10_flux.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class SecurityController {

    @GetMapping
    public Mono<List<String>> getRooms() {
        return Mono.just(List.of("Room A", "Room B", "Room C"));
    }

    @PostMapping
    public Mono<String> addRoom(@RequestParam("room") String room) {
        return Mono.just("Room added: " + room);
    }

    @DeleteMapping
    public Mono<String> deleteRoom(@RequestParam("room") String room) {
        return Mono.just("Room deleted: " + room);
    }
}
