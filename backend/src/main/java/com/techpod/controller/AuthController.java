package com.techpod.controller;
import com.techpod.dto.*;
import com.techpod.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.techpod.repository.UserRepository;


@RestController
// @RequestMapping("/api/auth")
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse entity = authService.login(loginRequest);
        return ResponseEntity.ok(entity);
    }

    // @PostMapping("/login")
    // public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest req) {
    //     return ResponseEntity.ok(authService.login(req));
    // }
//  NEED TO WORK ON THIS ENDPOINT TO RETURN THE CORRECT RESPONSE
    // @GetMapping("/me/data-expert")
    // public String getMethodName(@RequestParam String param) {
    //     return new String();
    // }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestParam String email) {
        if (!userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("User not found");
        }
        userRepository.deleteByEmail(email);
        return ResponseEntity.ok("User deleted successfully");
    }

}
