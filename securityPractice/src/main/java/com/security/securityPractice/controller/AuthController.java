package com.security.securityPractice.controller;


import com.security.securityPractice.DTO.*;
import com.security.securityPractice.entity.RefreshToken;
import com.security.securityPractice.entity.Role;
import com.security.securityPractice.entity.User;
import com.security.securityPractice.repository.RoleRepository;
import com.security.securityPractice.repository.UserRepository;
import com.security.securityPractice.security.JwtUtils;
import com.security.securityPractice.service.BlacklistService;
import com.security.securityPractice.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
        private PasswordEncoder passwordEncoder ;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private BlacklistService blacklistService;

    @PostMapping("/register")
    public ResponseEntity<?> register( @RequestBody RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.NOT_FOUND) ;
        }
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            return  ResponseEntity.badRequest().body(new MessageResponseDTO("Email is already used!"));
        }

        Set<Role> roles = new HashSet<>();
        if (registerRequestDTO.getRoles() != null && !registerRequestDTO.getRoles().isEmpty()) {

                for (Role r : registerRequestDTO.getRoles()){
//                        String rn = "ROLE_" + r.toUpperCase();
                        Role role = roleRepository.findByRoleName(String.valueOf(r).toUpperCase())
                                .orElseGet(()-> roleRepository.save(Role.builder().roleName(String.valueOf(r)).build()));
                    roles.add(role);
                }

        }else {
            Role role = roleRepository.findByRoleName("ROLE_USER")
                    .orElseGet(()->
                            roleRepository.save(Role.builder().roleName("ROLE_USER").build())
                            );
            roles.add(role);
        }

        User user = User.builder()
                 .username(registerRequestDTO.getUsername())
                 .email(registerRequestDTO.getEmail())
                    .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                    .roles(roles)
                    .enabled(true)
                    .build();
         userRepository.save(user);

        return ResponseEntity.ok(new MessageResponseDTO("User registered successfully."));
    }




    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequestDTO tokenRefreshRequestDTO) {
        try {
            String refreshToken = tokenRefreshRequestDTO.getRefreshToken();
            
            return refreshTokenService.findByToken(refreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        String username = user.getUsername();
                        String token = jwtUtils.generateAccessToken(username);
                        return ResponseEntity.ok(new JwtResponseDTO(token, refreshToken));
                    })
                    .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error refreshing token: " + e.getMessage()));
        }
    }


}
