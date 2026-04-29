package com.techpod.model;
import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate the ID
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;

    private LocalDateTime consentTimestamp;

    private boolean anonymized;

    private LocalDateTime anonymizedAt;

    private LocalDateTime dataRetentionExpiry;

    private LocalDateTime privatePolicyAcceptedAt;

    private String privatePolicyVersion;

    private boolean marketingConsent;

    private LocalDateTime marketingConsentAt;

}