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
            throw new IllegalArgumentException("Privacy consent is required for registration");
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
        return new JwtResponse(token, user.getEmail(), user.getRole().name(), user.getFirstName());
    }

    public JwtResponse login(LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new JwtResponse(token, user.getEmail(), user.getRole().name(), user.getFirstName());
    }

    // public void register(RegisterRequest request){
    //     boolean privacyConsent = request.isPrivacyConsent();
    //     if(!privacyConsent){
    //         throw new IllegalArgumentException("Privacy consent is required for registration");
    //     }else{
    //         privacyPolicyAccepted = True;
    //         privacyConsentTimestamp = LocalDateTime.now();

            // Proceed with registration logic
            // User user = new User();
            // user.setFirstName(request.getFirstName());
            // user.setLastName(request.getLastName());
            // user.setEmail(request.getEmail());
            // user.setPassword(passwordEncoder.encode(request.getPassword()));
            // user.setRole(Role.CUSTOMER);
            // user.setMarketingConsent(request.isMarketingConsent());
            // user.setConsentTimestamp(LocalDateTime.now());
            // userRepository.save(user);
        // }
    // }
}
