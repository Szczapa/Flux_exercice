package org.example.exercice13_flux;

import org.example.exercice13_flux.service.PlaylistService;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class PlaylistServiceTest {
    public final PlaylistService playlistService = new PlaylistService();

    @Test
    void testPlaylistRock() {
        StepVerifier.create(playlistService.getPlaylist("Rock"))
                .expectSubscription()
                .expectNext("Bohemian Rhapsody", "Hotel California", "Stairway to Heaven")
                .verifyComplete();
    }

    @Test
    void testPlaylistJazz() {
        StepVerifier.create(playlistService.getPlaylist("POP"))
                .expectSubscription()
                .expectNext("Thriller", "Like a Virgin", "Billie Jean")
                .verifyComplete();
    }

    @Test
    void testErrorPlaylist() {
        StepVerifier.create(playlistService.getPlaylist("test"))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException
                        && throwable.getMessage().equals("Genre test not supported"))
                .verify();
    }
}
