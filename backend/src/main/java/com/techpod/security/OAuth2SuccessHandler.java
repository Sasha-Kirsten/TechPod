package com.techpod.security;

import com.techpod.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    public OAuth2SuccessHandler(UserDetailsService userDetailsService, JwtUtil jwtUtil){
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Implement your OAuth login success handling logic here
        super.onAuthenticationSuccess(request, response, authentication);
    }
    public CustomUserDetails buildPrincipal(OAuth2User oauth2User){
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        CustomUserDetails user = (CustomUserDetails) userDetails;
        return user;
    }
    public String getOAuth2Email(Map<String, Object> attributes){
        return (String) attributes.get("email");
    }

    // Get the email from the OAuth2User and load the user details, then generate a JWT token for the user
    public Boolean getUserDataBase(String email){
        try {
            userDetailsService.loadUserByUsername(email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String generateToken(CustomUserDetails user) {
        return jwtUtil.generateToken(user.getUsername(), user.getPassword());
    }

    public boolean validateToken(String token, CustomUserDetails user) {
        return jwtUtil.validateToken(token, user);
    }


    


}
