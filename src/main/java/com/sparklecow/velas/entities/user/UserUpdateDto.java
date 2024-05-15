package com.sparklecow.velas.entities.user;


public record UserUpdateDto(
        String firstName,
        String lastName,
        String username,
        String email,
        String password){
}

