package com.sparklecow.velas.controllers;

import com.sparklecow.velas.config.jwt.JwtUtils;
import com.sparklecow.velas.entities.user.User;
import com.sparklecow.velas.services.CandleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/car")
@CrossOrigin(origins = "*")
@Tag(name = "ShoppingCar Controller", description = "ShoppingCar Controller")
public class ShoppingCarController {

    private final JwtUtils jwtUtils;

    /*@PostMapping("/{id}")
    public ResponseEntity<?> addProduc(@RequestHeader("Authorization") String authorizationHeader,
                                       HttpServletRequest request,
                                       @PathVariable Long id){
        if(SecurityContextHolder.getContext().getAuthentication()==null){
            return ResponseEntity.badRequest().build();
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String jwtToken = authorizationHeader.substring(7);

        // Ahora puedes hacer algo con el token JWT, como verificar su validez o extraer información
        if(!jwtUtils.validateToken(jwtToken, user)){
            return ResponseEntity.badRequest().build();
        }
        //user.getShoppingCar().addProduct();

        return ResponseEntity.ok("Token JWT recibido y usuario autenticado: " + user.getUsername());
    }*/
}
