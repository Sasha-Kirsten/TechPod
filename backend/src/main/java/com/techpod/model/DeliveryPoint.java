package com.techpod.model;

import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Table(name = "delivery_points")
@NoArgsConstructor
@Entity
@Builder
public class DeliveryPoint {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_route_id", nullable = false)
    private DeliveryRoute deliveryRoute;

    private double lat;
    private double lng;
}
