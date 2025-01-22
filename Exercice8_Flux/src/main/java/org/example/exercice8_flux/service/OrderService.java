package org.example.exercice8_flux.service;

import org.example.exercice8_flux.entity.Order;
import org.example.exercice8_flux.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Mono<Order> addOrder(Order order) {
        order.setStatus("PENDING"); // Statut par défaut
        order.setCreatedAt(LocalDateTime.now()); // Date de création
        return orderRepository.save(order);
    }

    public Mono<Order> updateOrderStatus(Long id, String newStatus) {
        return orderRepository.findById(id)
                .flatMap(order -> {
                    order.setStatus(newStatus);
                    return orderRepository.save(order);
                });
    }

    public Mono<Void> deleteOrderById(Long id) {
        return orderRepository.deleteById(id);
    }

    public Flux<Order> findOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public Flux<Order> findOrdersByCustomerName(String customerName) {
        return orderRepository.findByCustomerName(customerName);
    }

    public Flux<Order> getPagedOrders(int page, int size) {
        return orderRepository.findAll()
                .skip((long) page * size)
                .take(size);
    }
}
