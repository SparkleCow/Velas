package com.sparklecow.velas.controllers;

import com.sparklecow.velas.config.jwt.JwtUtils;
import com.sparklecow.velas.entities.user.User;
import com.sparklecow.velas.services.ShoppingCarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/car")
@CrossOrigin(origins = "*")
@Tag(name = "ShoppingCar Controller", description = "ShoppingCar Controller")
public class ShoppingCarController {

    private final JwtUtils jwtUtils;
    private final ShoppingCarService shoppingCarService;
    private final UserDetailsService userDetailsService;
    @PostMapping("/{id}")
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String authorizationHeader,
                                        @PathVariable Long id,
                                        @RequestParam(required = false) Long amount) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return ResponseEntity.badRequest().build();
        }

        String jwtToken = authorizationHeader.substring(7);
        User user = (User) userDetailsService.loadUserByUsername(jwtUtils.extractUsername(jwtToken));

        if (!jwtUtils.validateToken(jwtToken, user)) {
            return ResponseEntity.badRequest().build();
        }

        if (amount == null) {
            shoppingCarService.addProduct(id, user.getShoppingCar());
        } else {
            shoppingCarService.addProducts(id, amount, user.getShoppingCar());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<?> getCar(@RequestHeader("Authorization") String authorizationHeader){
        if(SecurityContextHolder.getContext().getAuthentication()==null){
            return ResponseEntity.badRequest().build();
        }
        String jwtToken = authorizationHeader.substring(7);
        User user = (User) userDetailsService.loadUserByUsername(jwtUtils.extractUsername(jwtToken));
        if(!jwtUtils.validateToken(jwtToken, user)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user.getShoppingCar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@RequestHeader("Authorization") String authorizationHeader,
                                           @PathVariable Long id){
        if(SecurityContextHolder.getContext().getAuthentication()==null){
            return ResponseEntity.badRequest().build();
        }
        String jwtToken = authorizationHeader.substring(7);
        User user = (User) userDetailsService.loadUserByUsername(jwtUtils.extractUsername(jwtToken));
        if(!jwtUtils.validateToken(jwtToken, user)){
            return ResponseEntity.badRequest().build();
        }
        shoppingCarService.removeProduct(id, user.getShoppingCar());
        return ResponseEntity.ok("Candle deleted");
    }

    @DeleteMapping("/{id}/deleteProducts")
    public ResponseEntity<?> deleteProducts(@RequestHeader("Authorization") String authorizationHeader,
                                           @PathVariable Long id){
        if(SecurityContextHolder.getContext().getAuthentication()==null){
            return ResponseEntity.badRequest().build();
        }
        String jwtToken = authorizationHeader.substring(7);
        User user = (User) userDetailsService.loadUserByUsername(jwtUtils.extractUsername(jwtToken));
        if(!jwtUtils.validateToken(jwtToken, user)){
            return ResponseEntity.badRequest().build();
        }
        shoppingCarService.removeProducts(id, user.getShoppingCar());
        return ResponseEntity.ok("Candles deleted");
    }

    @DeleteMapping("/{id}/deleteAllProducts")
    public ResponseEntity<?> deleteAllProducts(@RequestHeader("Authorization") String authorizationHeader,
                                               @PathVariable Long id){
        if(SecurityContextHolder.getContext().getAuthentication()==null){
            return ResponseEntity.badRequest().build();
        }
        String jwtToken = authorizationHeader.substring(7);
        User user = (User) userDetailsService.loadUserByUsername(jwtUtils.extractUsername(jwtToken));
        if(!jwtUtils.validateToken(jwtToken, user)){
            return ResponseEntity.badRequest().build();
        }
        shoppingCarService.removeAllProducts();
        return ResponseEntity.ok("Candle deleted");
    }
}
