package com.techpod.model;
import java.time.LocalDateTime;

// import org.springframework.cglib.core.Local;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = true)
    private String provider;

    // private boolean marketingConsent;

    private LocalDateTime consentTimestamp;

    private boolean anonymized;

    private LocalDateTime anonymizedAt;

    private LocalDateTime dataRetentionExpiry;

    private LocalDateTime privatePolicyAcceptedAt;

    private String privatePolicyVersion;

    private boolean marketingConsent;

    private LocalDateTime marketingConsentAt;

}