package com.example.zoom.Security;





import com.example.zoom.entity.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import static com.example.zoom.utils.AppConstants.*;


/**
 * Jwt Token Utilities.
 */
@Component
public class JwtTokenUtil implements Serializable {

    /**
     * Get username from token.
     *
     * @param token the token
     * @return the username
     */
    String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Get expiration date from token.
     *
     * @param token the token
     * @return the expiration date
     */
    private Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Get claims from token.
     *
     * @param token          the token
     * @param claimsResolver the claims resolver
     * @param <T>            the function argument
     * @return the claim
     */
    private <T> T getClaimFromToken(final String token,
                                    final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Get all claims from the token.
     *
     * @param token the token
     * @return the claims
     */
    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the token expired.
     *
     * @param token the token
     * @return true/false
     */
    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token by user.
     *
     * @param user       the user
     * @param rememberMe remember me
     * @return the token
     */
    public String generateToken(final AuthUser user, final boolean rememberMe) {
        return doGenerateToken(user.getEmail(),
                new SimpleGrantedAuthority(user.getRole()),
                rememberMe);
    }

    /**
     * Get token validity.
     *
     * @param rememberMe remember me
     * @return the token validity
     */
    private Date getTokenValidity(final boolean rememberMe) {
        if (rememberMe) {
            return new Date(System.currentTimeMillis()
                    + REMEMBER_ME_TOKEN_VALIDITY_SECONDS * 1000);
        } else {
            return new Date(System.currentTimeMillis()
                    + TOKEN_VALIDITY_SECONDS * 1000);
        }
    }

    /**
     * Generate token by email and authorities.
     *
     * @param email       the username
     * @param authorities the authorities
     * @param rememberMe  remember me
     * @return the token
     */
    private String doGenerateToken(final String email,
                                   final GrantedAuthority authorities,
                                   final boolean rememberMe) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("scopes", authorities);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("CallSlate")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getTokenValidity(rememberMe))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Validate token.
     *
     * @param token       the token
     * @param userDetails the user details
     * @return true/false
     */
    Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }

}

