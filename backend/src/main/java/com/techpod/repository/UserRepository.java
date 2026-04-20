package com.techpod.repository;
import com.techpod.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
    void deleteById(Integer id);
    void findAllEmail(String email);
    void setFirstName(String name);
    void setLastName(String name);
    String getUsername(String username);
}
