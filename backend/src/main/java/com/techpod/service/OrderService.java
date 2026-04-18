package com.techpod.service;

import com.techpod.dto.CheckoutRequest;
import com.techpod.exception.ResourceNotFoundException;
import com.techpod.model.*;
import com.techpod.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final LaptopRepository laptopRepository;
    private final UserRepository userRepository;

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
        return orderRepository.save(order);
    }
}
