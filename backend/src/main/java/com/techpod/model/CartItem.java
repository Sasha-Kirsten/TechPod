package com.techpod.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "cart_items",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "laptop_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "laptop_id", nullable = false)
    private Laptop laptop;

    private int quantity;
}
