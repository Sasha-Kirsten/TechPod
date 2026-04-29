package com.techpod.controller;

import com.techpod.model.DriverLocation;
import com.techpod.model.DeliveryPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DriverController {

    // @GetMapping("/api/driver-locations")
    // public List<DriverLocation> getDriverLocations() {
    //     return List.of(
    //         new DriverLocation(1, 51.5074, -0.1278), // Central London
    //         new DriverLocation(2, 51.5155, -0.1419), // Oxford Street
    //         new DriverLocation(3, 51.5033, -0.1195)  // London Eye
    //     );
    // }

    // @GetMapping("/api/delivery-points")
    // public List<DeliveryPoint> getDeliveryPoints() {
    //     return List.of(
    //         new DeliveryPoint(1, 51.5098, -0.1180), // Trafalgar Square
    //         new DeliveryPoint(2, 51.5007, -0.1246), // Big Ben
    //         new DeliveryPoint(3, 51.5200, -0.1550)  // Regent's Park
    //     );
    // }
}
