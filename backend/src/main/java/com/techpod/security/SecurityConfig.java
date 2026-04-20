package com.techpod.security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.config.Customizer;
import org.springframework.web.cors.*;
import java.util.List;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import java.io.IOException;
@Component
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler OAuth2SuccessHandler;
    private final JwtAuthFilter jwtAuthFilter;

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         .cors(c -> c.configurationSource(corsConfigurationSource()))
    //         .csrf(csrf -> csrf.disable())
    //         .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/api/auth/**").permitAll()
    //             .requestMatchers(HttpMethod.GET, "/api/laptops/**").permitAll()
    //             .requestMatchers("/api/admin/**").hasRole("ADMIN")
    //             .requestMatchers("/api/dispatch/**").hasAnyRole("ADMIN", "DISPATCHER")
    //             .anyRequest().authenticated()
    //         )
    //         .oauth2Login(oauth -> oauth
    //             .successHandler(OAuth2SuccessHandler)
    //         )
    //         .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    //     return http.build();
    // }

    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(c -> c.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/auth/**",
                    "/oauth2/**",
                    "/login/oauth2/**").permitAll()
                    .requestMatchers("/api/laptops/**").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .successHandler(OAuth2SuccessHandler)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    // @Bean
    // public class OAuth_LoginSuccessHandler extends OncePerRequestFilter {
    //     private final UserDetailsService userDetailsService;
    //     private final JwtUtil jwtUtil;
    //     public OAuth_LoginSuccessHandler(UserDetailsService userDetailsService, JwtUtil jwtUtil){
    //         this.userDetailsService = userDetailsService;
    //         this.jwtUtil = jwtUtil;
    //     }

        // @Override
        // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //     // Implement your OAuth login success handling logic here
        //     final String authrizationHeader = request.getHeader("Authorization");
        //     String username = null;
        //     String jwt = null;
        //     if (authrizationHeader != null && authrizationHeader.startsWith("Bearer ")) {
        //         jwt = authrizationHeader.substring(7);
        //         username = jwtUtil.extractUsername(jwt);
        //     }
        //     if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        //         UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        //         if (jwtUtil.validateToken(jwt, userDetails)) {
        //             UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        //                     userDetails, null, userDetails.getAuthorities());
        //             authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //             SecurityContextHolder.getContext().setAuthentication(authToken);
        //         }
        //     }
        //     filterChain.doFilter(request, response);
        // }
    // }
}
