package com.sparklecow.velas.entities.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserLoginDto(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Password is required")
        @NotNull(message = "Password is required")
        @Size(min=8, message = "Password must be at least 8 characters long")
        String password
){
}
