package com.techpod.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverLocation {
    private int driverId;
    private double lat;
    private double lng;
}
