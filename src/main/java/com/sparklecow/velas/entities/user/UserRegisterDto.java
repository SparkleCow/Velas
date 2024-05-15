package com.sparklecow.velas.entities.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String username,
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 8)
        String password
) {
}
