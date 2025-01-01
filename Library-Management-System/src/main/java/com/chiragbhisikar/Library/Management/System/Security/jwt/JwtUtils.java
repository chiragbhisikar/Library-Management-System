package com.chiragbhisikar.Library.Management.System.Security.jwt;


import com.chiragbhisikar.Library.Management.System.Security.user.ProfileDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class JwtUtils {
    private static final String SECRET = "36763979244226452948404D635166546A576D5A7134743777217A25432A462D";
    private static final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());

//    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    //    Generate Token Here
    public String generateTokenForUser(Authentication authentication) {
        ProfileDetails profileDetails = (ProfileDetails) authentication.getPrincipal();
        List<String> roles = profileDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        //  Collecting Data
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", profileDetails.getId());
        claims.put("email", profileDetails.getUsername());
        claims.put("role", "USER");

        Date currentDate = new Date();
        Date expireDate = new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000));

        String token = Jwts.builder()
                .setClaims(claims) // Set custom claims
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        System.out.println("New token :");
        System.out.println(token);

        return token;

    }

    //    Login -> Validate Token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("email");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect", ex.fillInStackTrace());
        }
    }
}
