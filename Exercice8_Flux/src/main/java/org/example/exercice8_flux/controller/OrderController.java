package org.example.exercice8_flux.controller;

import org.example.exercice8_flux.entity.Order;
import org.example.exercice8_flux.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public Mono<ServerResponse> getOrders() {
        return orderService.getAllOrders()
                .collectList() // Convertir Flux en List pour une réponse complète
                .flatMap(orders -> ServerResponse.ok().bodyValue(orders))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    @GetMapping("/{id}")
    public Mono<ServerResponse> getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .flatMap(order -> ServerResponse.ok().bodyValue(order))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    @PostMapping
    public Mono<ServerResponse> createOrder(@RequestBody Order order) {
        return orderService.addOrder(order)
                .flatMap(savedOrder -> ServerResponse.ok().bodyValue(savedOrder))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }


    @PutMapping("/{id}")
    public Mono<ServerResponse> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status)
                .flatMap(updatedOrder -> ServerResponse.ok().bodyValue(updatedOrder))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    // Supprimer une commande par ID
    @DeleteMapping("/{id}")
    public Mono<ServerResponse> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrderById(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.notFound().build());
    }


    @GetMapping("/search")
    public Mono<ServerResponse> orderByStatus(@RequestParam(name = "status") String status) {
        return orderService.findOrdersByStatus(status)
                .collectList()
                .flatMap(orders -> ServerResponse.ok().bodyValue(orders))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    @GetMapping("/customer/{customerName}")
    public Mono<ServerResponse> getOrdersByCustomer(@PathVariable String customerName) {
        return orderService.findOrdersByCustomerName(customerName)
                .collectList()
                .flatMap(orders -> ServerResponse.ok().bodyValue(orders))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    @GetMapping("/paged")
    public Mono<ServerResponse> getPagedOrders(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size) {
        return orderService.getPagedOrders(page, size)
                .collectList()
                .flatMap(orders -> ServerResponse.ok().bodyValue(orders))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
