package org.example.exercice8_flux.repository;

import org.example.exercice8_flux.entity.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
    Flux<Order> findByStatus(String status); // Rechercher par statut
    Flux<Order> findByCustomerName(String customerName); // Rechercher par nom de client
}
