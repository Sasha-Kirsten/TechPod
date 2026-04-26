package com.techpod.service;

import com.techpod.dto.CartItemRequest;
import com.techpod.exception.ResourceNotFoundException;
import com.techpod.model.*;
import com.techpod.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final LaptopRepository laptopRepository;
    private final UserRepository userRepository;

    private User getUser(String email) {
        return (User) userRepository.findByEmail(email);
    }

    public List<CartItem> getCart(String email) {
        return cartItemRepository.findByUser(getUser(email));
    }

    public CartItem addToCart(String email, CartItemRequest req) {
        User user = getUser(email);
        Laptop laptop = laptopRepository.findById(req.getLaptopId())
                .orElseThrow(() -> new ResourceNotFoundException("Laptop not found"));
        var existing = cartItemRepository.findByUserAndLaptopId(user, laptop.getId());
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + req.getQuantity());
            return cartItemRepository.save(item);
        }
        return cartItemRepository.save(CartItem.builder().user(user).laptop(laptop).quantity(req.getQuantity()).build());
    }

    public CartItem updateQuantity(String email, Long itemId, int quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (!item.getUser().getEmail().equals(email)) throw new IllegalArgumentException("Forbidden");
        if (quantity <= 0) { cartItemRepository.delete(item); return null; }
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    public void removeItem(String email, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (!item.getUser().getEmail().equals(email)) throw new IllegalArgumentException("Forbidden");
        cartItemRepository.delete(item);
    }

    public void clearCart(String email) { cartItemRepository.deleteByUser(getUser(email)); }
}
