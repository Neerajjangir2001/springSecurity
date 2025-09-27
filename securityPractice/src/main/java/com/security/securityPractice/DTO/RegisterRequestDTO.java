package com.security.securityPractice.DTO;


import com.security.securityPractice.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequestDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank @Size(min = 6, max = 12)
    private String password;

    @NotBlank
    @Email
    private String email;

    private Set<Role> roles;


}
