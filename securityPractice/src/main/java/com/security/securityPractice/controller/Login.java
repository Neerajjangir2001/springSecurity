package com.security.securityPractice.controller;

import com.security.securityPractice.DTO.JwtResponseDTO;
import com.security.securityPractice.DTO.LoginRequestDTO;
import com.security.securityPractice.entity.RefreshToken;
import com.security.securityPractice.entity.User;
import com.security.securityPractice.repository.RefreshTokenRepository;
import com.security.securityPractice.repository.UserRepository;
import com.security.securityPractice.security.JwtUtils;
import com.security.securityPractice.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class Login {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService  refreshTokenService;


    @PostMapping()
    public ResponseEntity<?> login( @RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        String accessToken = jwtUtils.generateAccessToken(loginRequestDTO.getUsername());
        User user = userRepository.findByUsername(loginRequestDTO.getUsername()).orElseThrow();
        RefreshToken referToken = refreshTokenService.createReferToken(user);

        return ResponseEntity.ok(new JwtResponseDTO(accessToken, referToken.getToken()));


    }
}
