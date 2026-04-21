package com.techpod.security;
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
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Implement your OAuth login success handling logic here

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        if(email == null){
            response.sendRedirect("http://localhost:5173/login?error=Email not found in OAuth2 provider");
            return;
        }

        final String finalEmail = email;
        final String finalName = name != null ? name : "Unknown";

        User user = userRepository.findByEmail(finalEmail).orElseGet(() -> userRepository.save(User.builder().email(finalEmail).name(finalName).role(Role.USER).provider("google").build()));
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        response.sendRedirect("http://localhost:5173/oauth2/redirect?token=" + token);
    }
}
