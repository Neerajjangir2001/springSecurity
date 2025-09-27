package com.security.securityPractice.repository;

import com.security.securityPractice.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface BlackListTokenRepository  extends JpaRepository<BlackListToken,Long> {

    Optional<BlackListToken> findByToken(String token);

    void deleteByExpiryDateBefore(Instant instant);
}
