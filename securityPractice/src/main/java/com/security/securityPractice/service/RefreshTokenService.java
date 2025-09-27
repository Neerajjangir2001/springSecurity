package com.security.securityPractice.service;


import com.security.securityPractice.entity.RefreshToken;
import com.security.securityPractice.entity.User;
import com.security.securityPractice.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${app.jwtRefreshExpirationMs}")
    private long refreshExpirationMs;
    private final RefreshTokenRepository repo;

    public RefreshTokenService(RefreshTokenRepository repo) {
        this.repo = repo;
    }

   @Transactional
    public RefreshToken createReferToken(User user) {
       Optional<RefreshToken> byUser = repo.findByUser(user);
       if (byUser.isPresent()) {
           RefreshToken token = byUser.get();
           token.setToken(UUID.randomUUID().toString());
           token.setExpiryDate(Instant.now().plusSeconds(refreshExpirationMs));
           return  repo.save(token);

       }

        RefreshToken token = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
                .build();
         return repo.save(token);

    }

    public Optional<RefreshToken> findByToken(String token){
        return repo.findByToken(token);
    }


    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().isBefore(Instant.now())){
            repo.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
        return  token;
    }

    public int deleteByUserId(User user){
        return  repo.deleteByUser(user);

    }

}
