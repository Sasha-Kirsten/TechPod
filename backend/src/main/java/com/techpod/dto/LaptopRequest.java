package com.techpod.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class LaptopRequest {
    @NotBlank private String brand;
    @NotBlank private String model;
    @NotBlank private String processor;
    private int ramGb;
    @NotBlank private String storage;
    private String gpu;
    private double screenSizeInch;
    @NotNull @DecimalMin("0.01") private BigDecimal price;
    @Min(0) private int stockQuantity;
    private String imageUrl;
    private String description;
}
