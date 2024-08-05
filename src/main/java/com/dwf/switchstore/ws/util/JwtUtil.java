package com.dwf.switchstore.ws.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The JwtUtil class provides utility methods for generating, validating,
 * and extracting information from JWT tokens.
 */
public class JwtUtil {

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Map<String, String> userActiveTokens = new ConcurrentHashMap<>();
    private static final Map<String, Long> tokenBlacklist = new ConcurrentHashMap<>();

    /**
     * Generates a JWT token for the given username
     * @param username the username to generate a token for
     * @return the generated token
     */
    public static String generateToken(String username, int userId) {
        long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();

        // Invalidate old token if it exists
        String oldToken = userActiveTokens.put(username, token);
        if (oldToken != null) {
            blacklistToken(oldToken);
        }

        return token;
    }

    /**
     * Validates a JWT token
     * @param token the token to validate
     * @return true if the token is valid, false otherwise
     */
    public static boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            String username = claims.getBody().getSubject();

            // Check if the token is the active one for this user
            String activeToken = userActiveTokens.get(username);
            if (!token.equals(activeToken)) {
                return false;
            }

            return !isTokenBlacklisted(token, claims.getBody().getExpiration().getTime());
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Extracts the username from a JWT token
     * @param token the token to extract the username from
     * @return the username extracted from the token
     */
    public static String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Extracts the user ID from a JWT token
     * @param token the token to extract the user ID from
     * @return the user ID extracted from the token
     */
    public static int getUserIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().get("userId", Integer.class);
    }

    /**
     * Adds a token to the blacklist
     * @param token the token to blacklist
     */
    public static void blacklistToken(String token) {
        try {
            long expirationTime = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                    .parseClaimsJws(token).getBody().getExpiration().getTime();
            tokenBlacklist.put(token, expirationTime);

            String username = getUsernameFromToken(token);
            userActiveTokens.remove(username, token);
        } catch (JwtException e) { /* Token is invalid, no need to blacklist */ }
    }

    /**
     * Checks if a token is blacklisted
     * @param token the token to check
     * @param expirationTime the expiration time of the token
     * @return true if the token is blacklisted, false otherwise
     */
    private static boolean isTokenBlacklisted(String token, long expirationTime) {
        Long blacklistedExpiration = tokenBlacklist.get(token);
        if (blacklistedExpiration != null) {
            if (System.currentTimeMillis() > blacklistedExpiration) {
                tokenBlacklist.remove(token);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Removes expired tokens from the blacklist
     */
    public static void cleanupBlacklist() {
        long now = System.currentTimeMillis();
        tokenBlacklist.entrySet().removeIf(entry -> now > entry.getValue());
    }
}
