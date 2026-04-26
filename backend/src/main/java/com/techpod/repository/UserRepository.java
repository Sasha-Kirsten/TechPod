package com.techpod.repository;
import com.techpod.model.User;

import jakarta.transaction.Transactional;

// import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Integer id);
    // Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
    void deleteById(Integer id);
    // void findAllEmail(String email);
    List<User> findByEmail(String email);   
    // void setFirstName(String name);
    // void setLastName(String name);
    // String getUsername(String username);
    @Modifying
    @Query(value = "UPDATE users u SET u.email = CONCAT('deleted_', u.id, '@removed.com'), " +
        "u.name = 'Deleted User', u.password = NULL, u.provider = NULL, " +
        "u.marketing_consent = false, u.consent_timestamp = NULL, " +
        "u.anonymized = true, u.anonymized_at = CURRENT_TIMESTAMP, " +
        "u.data_retention_expiry = CURRENT_TIMESTAMP + INTERVAL '1 YEAR' " +
        "WHERE u.id = :userId", nativeQuery = true)
    void anonymizeUser(@Param("userId") Long userId);
    List <User> findByDataRetentionExpiryBefore(LocalDateTime date);
    LocalDateTime findDataRetentionExpiryById(Long id);
    LocalDateTime findDataRetentionStartTimeById(Long id);

    @Transactional
    void deleteByEmail(String email);


//     repository/UserRepository.java
//     Must have:
//          Anonymization update/query method (right to erasure)
//          Finder for expired retention (...Before(now))



}
