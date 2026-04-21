package com.techpod.repository;
import com.techpod.model.CartItem;
import com.techpod.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndLaptopId(User user, Long laptopId);
    void deleteByUser(User user);
}
