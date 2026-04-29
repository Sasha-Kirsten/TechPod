package com.techpod.service;
import com.techpod.dto.*;
import com.techpod.model.*;
import com.techpod.repository.UserRepository;
import com.techpod.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public JwtResponse register(RegisterRequest req) {
        if (!req.isPrivacyConsent()) {
            LocalDateTime timestamp = userRepository.findDataRetentionStartTimeById(LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.UTC));
            return new JwtResponse(null, null, null, null, timestamp); // Return empty response if privacy consent is not given
        }
        
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setMarketingConsent(req.isMarketingConsent());
        user.setConsentTimestamp(LocalDateTime.now());
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new JwtResponse(token, user.getEmail(), user.getRole().name(), user.getFirstName(), user.getConsentTimestamp());
    }

    public JwtResponse login(LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User user = (User) userRepository.findByEmail(req.getEmail());
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new JwtResponse(token, user.getEmail(), user.getRole().name(), user.getFirstName(), user.getConsentTimestamp());
    }
}
