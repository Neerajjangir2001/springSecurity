package com.security.securityPractice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRefreshRequestDTO {
    @NotBlank
    private String refreshToken;
}
