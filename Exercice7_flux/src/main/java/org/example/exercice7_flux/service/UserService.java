package org.example.exercice7_flux.service;

import org.example.exercice7_flux.entity.User;
import org.example.exercice7_flux.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Mono<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Mono<User> updateUser(int id, User updatedUser) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setUsername(updatedUser.getUsername());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setActive(updatedUser.isActive());
                    return userRepository.save(existingUser);
                });
    }

    public Mono<Void> deleteUser(int id) {
        return userRepository.deleteById(id);
    }
}
