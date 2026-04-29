package com.techpod.service;

import com.techpod.model.DeliveryRoute;
import com.techpod.model.DeliveryPoint;
import com.techpod.repository.DeliveryRouteRepository;
import com.techpod.repository.DeliveryPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryRouteService {

    private final DeliveryRouteRepository deliveryRouteRepository;
    private final DeliveryPointRepository deliveryPointRepository;

    public List<DeliveryRoute> getAllRoutes() {
        return deliveryRouteRepository.findAll();
    }

    public DeliveryRoute createRoute(String routeName, List<DeliveryPoint> points) {
        DeliveryRoute route = DeliveryRoute.builder()
            .routeName(routeName)
            .deliveryPoints(points)
            .build();
        points.forEach(point -> point.setDeliveryRoute(route));
        return deliveryRouteRepository.save(route);
    }

    public List<DeliveryPoint> getPointsByRoute(Long routeId){
        DeliveryRoute route = deliveryRouteRepository.findById(routeId)
            .orElseThrow(() -> new IllegalArgumentException("Route not found"));
        return route.getDeliveryPoints();
    }
}
