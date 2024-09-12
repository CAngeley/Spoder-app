package com.revature.spoder_app.Security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * This class is used to generate JWTs for users.
 */
@Component
public class JwtGenerator {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * This method is used to generate a JWT for a user.
     * @param auth The authentication of the user.
     * @param id The id of the user.
     * @return The JWT that was generated.
     */
    public String generateToken(Authentication auth, int id) {
        String email = auth.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
            .setSubject(email)
            .claim("id", id)
            .setIssuedAt(currentDate)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact();
        return token;
    }

    /**
     * This method is used to extract the email from a JWT.
     * @param token The JWT to extract the email from.
     * @return The email that was extracted from the JWT.
     */
    public String getUserEmailFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    /**
     * This method is used to validate a JWT.
     * @param token The JWT to validate.
     * @return True if the JWT is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
