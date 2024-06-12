package com.sparklecow.velas.config.jwt;

import com.sparklecow.velas.entities.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${application.security.jwt.secret_key}")
    private String SECRET_KEY;
    @Value("${application.security.jwt.expiration}")
    private Long EXPIRATION;

    public String generateToken(UserDetails User){
        Map<String, Object> extraClaims = new HashMap<>();
        return generateToken(User, extraClaims);
    }
    public String generateToken(UserDetails User, Map<String, Object> extraClaims){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(User.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(generateSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, User User) {
        try {
            Jwts.parserBuilder().setSigningKey(generateSignKey()).build().parseClaimsJws(token);
            return extractUsername(token).equals(User.getUsername()) && !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Error extracting claim: " + e.getMessage());
            throw e;
        }
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(generateSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Error parsing token: " + e.getMessage());
            throw e;
        }
    }

    private Key generateSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
