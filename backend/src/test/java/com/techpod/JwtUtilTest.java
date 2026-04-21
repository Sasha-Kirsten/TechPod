package com.techpod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.techpod.security.JwtUtil;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test 
    void shouldGenerateAndValidateToken(){
        String token = jwtUtil.generateToken("test@example.com", "USER");
        boolean isValid = jwtUtil.isTokenValid(token);
        assertTrue(isValid);
    }

    @Test 
    void shouldExtractEmailFromToken(){
        String token = jwtUtil.generateToken("test@example.com", "USER");
        String email = jwtUtil.extractEmail(token);
        assertEquals("test@example.com", email);
    }

    @Test
    void shouldRejectInvlaidToken(){
        assertFalse(jwtUtil.isTokenValid("invalid.token.here"));
    }
}