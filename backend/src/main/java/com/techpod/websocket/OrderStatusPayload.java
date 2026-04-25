package com.techpod.websocket;

import com.techpod.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor
public class OrderStatusPayload {

    private Long orderId;
    private OrderStatus status;
    private String message;
    
}
