package org.example.exercice8_flux.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Getter
@Setter
@Table("orders")
public class Order {
    @Id
    private Long id;
    private String customerName;
    private double totalAmount;
    private String status;
    private LocalDateTime createdAt;

    public Order(String customerName, double totalAmount, String status) {
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}
