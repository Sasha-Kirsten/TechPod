package com.techpod.service;

import com.techpod.dto.CheckoutRequest;
import com.techpod.exception.ResourceNotFoundException;
import com.techpod.model.*;
import com.techpod.repository.*;
import com.techpod.websocket.OrderStatusPayload;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
// @RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final LaptopRepository laptopRepository;
    private final UserRepository userRepository;

    private final SimpMessagingTemplate messagingTemplate; // For WebSocket notifications

    // private final OrderStatusPayload orderStatusPayload;

    public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository, LaptopRepository laptopRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.laptopRepository = laptopRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }


    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional
    public Order checkout(String email, CheckoutRequest req) {
        User user = getUser(email);
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) throw new IllegalArgumentException("Cart is empty");

        Order order = Order.builder().user(user).status(OrderStatus.PENDING)
                .shippingAddress(req.getShippingAddress()).build();

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : cartItems) {
            Laptop laptop = ci.getLaptop();
            if (laptop.getStockQuantity() < ci.getQuantity())
                throw new IllegalArgumentException("Insufficient stock for: " + laptop.getBrand() + " " + laptop.getModel());
            laptop.setStockQuantity(laptop.getStockQuantity() - ci.getQuantity());
            laptopRepository.save(laptop);
            BigDecimal line = laptop.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity()));
            total = total.add(line);
            order.getItems().add(OrderItem.builder().order(order).laptop(laptop)
                    .quantity(ci.getQuantity()).priceAtPurchase(laptop.getPrice()).build());
        }
        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);
        cartItemRepository.deleteByUser(user);
        // messagingTemplate.convertAndSend("/topic/admin/orders/" + saved.getUser().getId(),
        //     new OrderStatusPayload(saved.getId(), saved.getStatus(), "Order status updated.")
        // );
        messagingTemplate.convertAndSend("/topic/orders/" + saved.getUser().getId(),
            new OrderStatusPayload(saved.getId(), saved.getStatus(), "Order placed successfully.")
        );
        return saved;
    }

    public List<Order> getMyOrders(String email) {
        return orderRepository.findByUserOrderByCreatedAtDesc(getUser(email));
    }

    public List<Order> getAllOrders() { return orderRepository.findAllByOrderByCreatedAtDesc(); }

    public Order updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(status);
        Order saved = orderRepository.save(order);
        messagingTemplate.convertAndSend("/topic/orders/" + saved.getUser().getId(),
            new OrderStatusPayload(saved.getId(), saved.getStatus(), "Order status updated.")
        );

        messagingTemplate.convertAndSend("/topic/admin/orders/" + saved.getUser().getId(),
            new OrderStatusPayload(saved.getId(), saved.getStatus(), "Order status updated.")
        );

        return saved;

        // Order order = orderRepository.findById(id)
        //         .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        // order.setStatus(status);
        // return orderRepository.save(order);
    }



}
