package com.sparklecow.velas.controllers;

import com.sparklecow.velas.entities.user.*;
import com.sparklecow.velas.services.user.UserServiceImp;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Auth Controller", description = "Auth Controller")
public class AuthController {

    private final UserServiceImp userService;

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) throws ExpiredJwtException{
        try {
            boolean isValid = userService.validate(token);
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDto userRegisterDto) throws MessagingException {
        userService.create(userRegisterDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseAuthDto> authenticate(@RequestBody @Valid UserLoginDto userLoginDto){
        return ResponseEntity.ok(userService.authenticate(userLoginDto));
    }

    @PostMapping("/activate-account")
    public ResponseEntity<?> activateAccount(@RequestParam String token) throws MessagingException {
        userService.activateAccount(token);
        return ResponseEntity.ok().build();
    }

    //TODO Gestionar permisos solo a administradores

    @GetMapping()
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    //Todo Gestionar permisos a usuarios y administradores
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto){
        return ResponseEntity.ok(userService.update(userUpdateDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
