package com.sparklecow.velas.entities.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank
        String username,
        @NotBlank
        String password
){
}
