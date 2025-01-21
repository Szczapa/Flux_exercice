package org.example.exercice7_flux.repository;

import org.example.exercice7_flux.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository  extends ReactiveMongoRepository<User,Integer> {
    Mono<User> findByEmail(String email);
}
