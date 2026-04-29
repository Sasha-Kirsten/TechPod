package com.techpod.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class DeliveryRoute {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String routeName;

    @OneToMany(mappedBy = "deliveryRoute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryPoint> deliveryPoints;
    
}
