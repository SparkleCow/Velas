package com.sparklecow.velas.controllers;

import com.sparklecow.velas.entities.user.*;
import com.sparklecow.velas.exceptions.*;
import com.sparklecow.velas.services.user.UserServiceImp;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Auth Controller", description = "Auth Controller")
public class AuthController {

    private final UserServiceImp userService;

    /*Validate token and roles*/
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) throws ExpiredJwtException{
        String tokenValue = token.startsWith("Bearer ") ? token.substring(7) : token;
        try {
            return ResponseEntity.ok(userService.validate(tokenValue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @GetMapping("/validate/username")
    public ResponseEntity<Map<String, String>> validateTokenWithUsername(@RequestHeader("Authorization") String token) throws ExpiredJwtException {
        String tokenValue = token.startsWith("Bearer ") ? token.substring(7) : token;
        try {
            if(userService.validate(tokenValue)){
                String username = userService.extractUsername(tokenValue);
                Map<String, String> response = new HashMap<>();
                response.put("username", username);
                return ResponseEntity.ok(response);
            }
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @GetMapping("/validate/role")
    public ResponseEntity<Void> validateTokenWithRole(@RequestHeader("Authorization") String token) throws ExpiredJwtException,
            AdminRoleNotFoundException,
                                                                                                    ExpiredTokenException,
                                                                                                    InvalidTokenException {
        String tokenValue = token.startsWith("Bearer ") ? token.substring(7) : token;
        userService.validate(tokenValue);
        userService.validateAdminRole(tokenValue);
        return ResponseEntity.noContent().build();
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

    @PostMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestParam String token) throws MessagingException, ActivationTokenException {
        userService.activateAccount(token);
        return ResponseEntity.ok().build();
    }

    //Required admin, manager or worker rol
    @GetMapping()
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) throws UserNotFoundException {
        return ResponseEntity.ok(userService.update(userUpdateDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
