package com.techpod.controller;

import com.techpod.model.DeliveryRoute;
import com.techpod.service.DeliveryRouteService;
import com.techpod.model.DeliveryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class DeliveryRouteController {

    private final DeliveryRouteService deliveryRouteService;

    @GetMapping
    public ResponseEntity<List<DeliveryRoute>> getAllRoutes() {
        return ResponseEntity.ok(deliveryRouteService.getAllRoutes());
    }

    @PostMapping 
    public ResponseEntity<DeliveryRoute> createRoute(@RequestBody String routeName, @RequestBody List<DeliveryPoint> points){
        return ResponseEntity.ok(deliveryRouteService.createRoute(routeName, points));
    }


    @GetMapping("/{routeId}/points")
    public ResponseEntity<List<DeliveryPoint>> getPointsByRoute(@PathVariable Long routeId){
        return ResponseEntity.ok(deliveryRouteService.getPointsByRoute(routeId));
    }


    
}
