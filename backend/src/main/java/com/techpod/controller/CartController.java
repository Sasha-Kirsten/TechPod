package com.techpod.controller;

import com.techpod.dto.CartItemRequest;
import com.techpod.model.CartItem;
import com.techpod.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCart(@AuthenticationPrincipal UserDetails user) {
        return cartService.getCart(user.getUsername());
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@AuthenticationPrincipal UserDetails user,
                                              @Valid @RequestBody CartItemRequest req) {
        return ResponseEntity.ok(cartService.addToCart(user.getUsername(), req));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<?> updateQuantity(@AuthenticationPrincipal UserDetails user,
                                            @PathVariable Long itemId,
                                            @RequestBody Map<String, Integer> body) {
        CartItem item = cartService.updateQuantity(user.getUsername(), itemId, body.get("quantity"));
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeItem(@AuthenticationPrincipal UserDetails user,
                                           @PathVariable Long itemId) {
        cartService.removeItem(user.getUsername(), itemId);
        return ResponseEntity.noContent().build();
    }
}
