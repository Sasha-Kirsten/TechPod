package com.techpod.controller;

import com.techpod.dto.*;
import com.techpod.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.techpod.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok("User registered successfully");
        // return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    // @PostMapping("/UserDataBase")
    // public ResponseEntity<Boolean> checkUserInDatabase(@RequestParam String email) {
    //     boolean exists = userRepository.existsByEmail(email);
    //     return ResponseEntity.ok(exists);
    // }

    // @PostMapping("/UserDataBase")
    // public String postMethodName(@RequestBody String entity) {
    //     //TODO: process POST request
        
    //     return entity;
    // }
    
}
