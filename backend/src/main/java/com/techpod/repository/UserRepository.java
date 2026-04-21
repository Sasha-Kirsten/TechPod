package com.techpod.repository;
import com.techpod.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
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
    @Modifying
    @Query("UPDATE User u SET u.email = CONCAT('deleted_', u.id, '@removed.com'), u.name = 'Deleted User', u.password = NULL, u.provider = NULL, " +
           "u.marketingConsent = false, u.consentTimestamp = NULL, u.anonymized = true, " +
           "u.anonymizedAt = CURRENT_TIMESTAMP, u.dataRetentionExpiry = CURRENT_TIMESTAMP + INTERVAL '1 YEAR' " +
           "WHERE u.id = :userId")
    void anonymizeUser(@Param("userId") Long userId);
    List <User> findByDataRetentionExpiryBefore(LocalDateTime date);
}
