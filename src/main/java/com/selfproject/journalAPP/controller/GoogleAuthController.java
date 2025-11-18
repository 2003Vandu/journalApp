package com.selfproject.journalAPP.controller;

import com.selfproject.journalAPP.Repository.UserRepository;
import com.selfproject.journalAPP.Utilis.JwtUtils;
import com.selfproject.journalAPP.entity.User;
import com.selfproject.journalAPP.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/auth/google")
@Slf4j
public class GoogleAuthController
{
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallBack(@RequestParam String code) {
        try {
            log.info("Received Google OAuth2 code: {}", code);

            String tokenEndpoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", "http://localhost:8080/auth/google/callback"); // ✅ Use your actual backend URI
            params.add("grant_type", "authorization_code");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);

            if (tokenResponse.getStatusCode() != HttpStatus.OK || tokenResponse.getBody() == null || !tokenResponse.getBody().containsKey("id_token")) {
                log.error("Failed to retrieve ID token from Google: {}", tokenResponse);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to retrieve ID token from Google");
            }

            String idToken = (String) tokenResponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

            if (userInfoResponse.getStatusCode() != HttpStatus.OK || userInfoResponse.getBody() == null) {
                log.error("Failed to validate ID token: {}", userInfoResponse);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token");
            }

            Map<String, Object> userInfo = userInfoResponse.getBody();
            String email = (String) userInfo.get("email");
            log.info("Authenticated Google user: {}", email);

            try {
                userDetailsService.loadUserByUsername(email);
            } catch (Exception e) {
                log.info("User not found, creating new user: {}", email);
                User user = new User();
                user.setEmail(email);
                user.setUserName(email);
                user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                user.setRoles(Collections.singletonList("USER"));
                userRepository.save(user);
            }

            String jwtToken = jwtUtils.generateToken(email);
            if (jwtToken == null) {
                log.error("JWT generation failed for user: {}", email);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token generation failed");
            }

            log.info("Generated JWT for user: {}", email);
            return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));

        } catch (Exception e) {
            log.error("Exception occurred while handling Google callback", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
