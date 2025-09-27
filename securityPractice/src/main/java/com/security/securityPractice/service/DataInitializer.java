//package com.security.securityPractice.service;
//
//
//import com.security.securityPractice.entity.Role;
//import com.security.securityPractice.entity.User;
//import com.security.securityPractice.repository.RoleRepository;
//import com.security.securityPractice.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Set;
//
//@Configuration
//public class DataInitializer {
//
//
//    @Bean
//    CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
//        return args -> {
//            Role admin = roleRepo.findByRoleName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(Role.builder().roleName("ROLE_ADMIN").build()));
//            Role user = roleRepo.findByRoleName("ROLE_USER").orElseGet(() -> roleRepo.save(Role.builder().roleName("ROLE_USER").build()));
//
//            if (!userRepo.existsByUsername("admin")) {
//                User u = User.builder()
//                        .username("admin")
//                        .email("admin@example.com")
//                        .password(encoder.encode("adminpass"))
//                        .roles(Set.of(admin))
//                        .build();
//                userRepo.save(u);
//            }
//        };
//    }
//}
