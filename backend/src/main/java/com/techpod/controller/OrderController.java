package com.techpod.controller;

import com.techpod.dto.CheckoutRequest;
import com.techpod.model.*;
import com.techpod.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@AuthenticationPrincipal UserDetails user,
                                          @Valid @RequestBody CheckoutRequest req) {
        return ResponseEntity.ok(orderService.checkout(user.getUsername(), req));
    }

    @GetMapping("/my")
    public List<Order> myOrders(@AuthenticationPrincipal UserDetails user) {
        return orderService.getMyOrders(user.getUsername());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
    public List<Order> allOrders() { return orderService.getAllOrders(); }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id,
                                              @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(orderService.updateStatus(id, OrderStatus.valueOf(body.get("status"))));
    }

    @GetMapping("/api/delivery-locations")
    public List<DriverLocation> getDeliveryLocations() {
        return List.of(
            new DriverLocation(1, 37.7749, -122.4194),
            new DriverLocation(2, 34.0522, -118.2437)
        );
    }
}