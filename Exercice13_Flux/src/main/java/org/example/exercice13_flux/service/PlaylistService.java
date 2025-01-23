package org.example.exercice13_flux.service;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class PlaylistService {


    public Flux<String> getPlaylist(String genre) {

        if (genre == null || genre.isEmpty()) {
            return Flux.error( new IllegalArgumentException("Genre cannot be null or empty"));
        }

        genre = genre.toLowerCase();

        switch (genre) {
            case "pop" -> {
                return Flux.just("Thriller", "Like a Virgin", "Billie Jean").delayElements(Duration.ofMillis(500));
            }
            case "rock" -> {
                return Flux.just("Bohemian Rhapsody", "Hotel California", "Stairway to Heaven").delayElements(Duration.ofMillis(500));
            }
        }
       return Flux.error(new IllegalArgumentException("Genre " + genre + " not supported")) ;
    }
}
