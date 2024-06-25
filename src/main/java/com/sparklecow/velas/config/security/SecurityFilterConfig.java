package com.sparklecow.velas.config.security;

import com.sparklecow.velas.config.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        //Authentications for candle controller
                        .requestMatchers(HttpMethod.GET, "/api/v1/candle").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/candle/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/candle/category/{category}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/candle").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/candle/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/candle/{id}").hasAuthority("ADMIN")
                        //Authentications for Auth and Users controller
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/validate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/validate/username").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/validate/role").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/activate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth").hasAnyAuthority("ADMIN", "WORKER", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/{id}").hasAnyAuthority("ADMIN", "WORKER", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/auth/{id}").hasAnyAuthority("ADMIN", "WORKER", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/auth/{id}").hasAnyAuthority("ADMIN", "WORKER", "MANAGER")
                        //Open endpoints
                        .anyRequest().permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }
}
