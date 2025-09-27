package com.security.securityPractice.service;


import com.security.securityPractice.entity.BlackListToken;
import com.security.securityPractice.repository.BlackListTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class BlacklistService {

     private final BlackListTokenRepository repo;


     public void blacklist(String token, Instant expiration){
         BlackListToken build = BlackListToken.builder().token(token).expiryDate(expiration).build();
         repo.save(build);
     }

     public boolean isBlacklisted(String token){
         return repo.findByToken(token).isPresent();
     }

        public void cleanup(){
         repo.deleteByExpiryDateBefore(Instant.now());
        }

}
