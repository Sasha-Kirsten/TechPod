package com.techpod.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {
    @NotNull private Long laptopId;
    @Min(1)  private int quantity;
}
