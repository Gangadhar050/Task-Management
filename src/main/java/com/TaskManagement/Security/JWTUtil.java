package com.TaskManagement.Security;

import com.TaskManagement.Entity.User;
import com.TaskManagement.Enum.Permission;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;


@Component
public class  JWTUtil {

    private final Key key ;// Generate a secure random key for HS256
    private final long expirationTime = 1000L*60*60*24; // 24 hours in milliseconds


    // Generate a JWT token for a given username
    public JWTUtil() {
        String secret=System.getenv("JWT_SECRET");// Fetch secret key from environment variable
        if(secret==null||secret.isEmpty()){
            secret="ReplaceThisWithASecretKeyThatIsAtLeast32Chars!";
        }
        key = Keys.hmacShaKeyFor(secret.getBytes());// Generate a secure random key for HS256
    }

    // For testing purposes
    public Key getKey() {
        return key;
    }

    // For testing purposes
    public long getExpirationTime() {
        return expirationTime;
    }


    // Generate a JWT token for a given username
    public String generateToken(User user) {

        //Permission configuration
        Set<Permission> permissions= PermissionConfig.getrolePermission().get(user.getRole());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(user.getUserEmail())

                //for permission
                .claim("role",user.getRole().name())
                .claim("permission",permissions.stream().map(Enum::name).collect(Collectors.toList()))

                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate a JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Validate a JWT token and return the username if valid
    public String getSubject(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //For PermissionConfig
    public Claims getClaim(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }


}


