package com.techpod.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "laptops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String brand;
    @NotBlank private String model;
    @NotBlank private String processor;
    private int ramGb;
    @NotBlank private String storage;
    private String gpu;
    private double screenSizeInch;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;

    @Min(0)
    private int stockQuantity;

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
