package com.techpod.security;

import com.techpod.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import com.techpod.model.User;
import com.techpod.model.Role;
import com.techpod.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    

    // private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    // private String name;
    // private String email;
    // private String provider;

    // public void OAuth2SuccessHandler(UserDetailsService userDetailsService, JwtUtil jwtUtil, UserRepository userRepository){
    //     this.userDetailsService = userDetailsService;
    //     this.jwtUtil = jwtUtil;
    //     this.userRepository = userRepository;
    // }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Implement your OAuth login success handling logic here
        // super.onAuthenticationSuccess(request, response, authentication);

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        // String provider = oauth2User.getAttribute("provider");

        if(email == null){
            response.sendRedirect("http://localhost:5173/login?error=Email not found in OAuth2 provider");
            return;
        }

        final String finalEmail = email;
        final String finalName = name != null ? name : "Unknown";
        // final String finalProvider = provider != null ? provider : "Unknown";


        User user = userRepository.findByEmail(finalEmail).orElseGet(() -> userRepository.save(User.builder().email(finalEmail).name(finalName).role(Role.USER).provider("google").build()));
        // UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        response.sendRedirect("http://localhost:5173/oauth2/redirect?token=" + token);
    }




    // public CustomUserDetails buildPrincipal(OAuth2User oauth2User){
    //     Map<String, Object> attributes = oauth2User.getAttributes();
    //     String email = (String) attributes.get("email");
    //     UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    //     CustomUserDetails user = (CustomUserDetails) userDetails;
    //     return user;
    // }
    // public String getOAuth2Email(Map<String, Object> attributes){
    //     return (String) attributes.get("email");
    // }

    // // Get the email from the OAuth2User and load the user details, then generate a JWT token for the user
    // public Boolean getUserDataBase(String email){
    //     try {
    //         userDetailsService.loadUserByUsername(email);
    //         return true;
    //     } catch (Exception e) {
    //         return false;
    //     }
    // }

    // public String generateToken(CustomUserDetails user) {
    //     return jwtUtil.generateToken(user.getUsername(), user.getPassword());
    // }

    // public boolean validateToken(String token, CustomUserDetails user) {
    //     return jwtUtil.validateToken(token, user);
    // }


    


}
