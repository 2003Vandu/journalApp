package com.selfproject.journalAPP.config;

import com.selfproject.journalAPP.filter.Jwtfilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.selfproject.journalAPP.service.UserDetailsServiceImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private Jwtfilter jwtfilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(request -> request
                        .requestMatchers("/public/**").permitAll() // Anyone can access without login
                        .requestMatchers("/journal/**", "/user/**").authenticated() // Must be logged in
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Must be logged in AND have ADMIN role
                        .anyRequest().authenticated()) // All other endpoints require login
                // .httpBasic(Customizer.withDefaults()) // Use Basic Auth (username + password in header) — removed for JWT
                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before default auth
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF because it's a stateless API
                // CSRF protection is used to prevent malicious requests from browsers using stored cookies.
                // In stateless APIs, we don’t use sessions or cookies—each request is self-contained.
                // So CSRF protection is not needed, and we disable it.
                .build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
    
}
