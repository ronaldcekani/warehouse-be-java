package com.example.warehousemanagement.security.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Component
public final class JwtTokenUtil {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    /**
     * The algorithm to be used for creating signatures.
     */
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    /**
     * the secret used for token encryption as given in the properties.
     */
    @Value("${security.jwt.secret}")
    private String secret;

    /**
     * the token expiration as given in the properties.
     */
    @Value("${security.jwt.expiration}")
    private Long expiration;

    /**
     * Extracts the username from the given token.
     *
     * @param token the token.
     * @return the extracted username.
     */
    public String getUsernameFromToken(final String token) {
        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    /**
     * Extracts the creation date from the given token.
     *
     * @param token the token.
     * @return the extracted creation date.
     */
    public Date getCreatedDateFromToken(final String token) {
        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            return claims.getIssuedAt();
        }
        return null;
    }

    /**
     * Internally extracts the information of the given token and returns a <code>Claims</code> value objects containing
     * the information.
     *
     * @param token the token.
     * @return the extracted information.
     */
    private Claims getClaimsFromToken(final String token) {
        try {
            final String accessToken = token.substring("Bearer".length()).trim();
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(accessToken).getBody();
        } catch (final IllegalArgumentException | JwtException e) {
            LOGGER.debug("Error extracting claims information from token: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Generates an expiration date for a newly created token.
     *
     * @return the expiration date.
     */
    private Date generateExpirationDate() {
        return Date.from(Instant.now().plusSeconds(expiration));
    }

    /**
     * Checks if the given token is already expired.
     *
     * @param token the token.
     * @return true, if the token is expired or does not contain a expiration information.
     */
    private Boolean isTokenExpired(final String token) {
        final Claims claims = getClaimsFromToken(token);
        if (claims != null && claims.getExpiration() != null) {
            return claims.getExpiration().before(new Date());
        }
        return true;
    }

    /**
     * Checks if the given token was created before the user's password was changed, s.t. the user should re-login.
     *
     * @param created           the creation date if the token.
     * @param lastPasswordReset the date of the last password change.
     * @return true, if the token was created before the user's password was changed.
     */
    private Boolean isCreatedBeforeLastPasswordReset(final Date created, final Date lastPasswordReset) {
        return lastPasswordReset != null && created.before(lastPasswordReset);
    }

    /**
     * Creates a new token based on the given <code>UserDetails</code> information.
     *
     * @param userDetails the <code>UserDetails</code> used for token generation.
     * @return the new token.
     */
    public String generateToken(final UserDetails userDetails) {
        final Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.setIssuedAt(new Date());
        claims.setExpiration(generateExpirationDate());
        return generateToken(claims);
    }

    /**
     * Internally creates a new token based on the given information.
     *
     * @param claims a Map containing the information needed.
     * @return the new token.
     */
    private String generateToken(final Claims claims) {
        return Jwts.builder().setClaims(claims).signWith(SIGNATURE_ALGORITHM, secret).compact();
    }

    /**
     * Checks if the given token may be refreshed as the user's password did not change.
     *
     * @param token             the token.
     * @param lastPasswordReset The time of the last password change of the user.
     * @return true, if the token may be refreshed.
     */
    public Boolean canTokenBeRefreshed(final String token, final Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && (!isTokenExpired(token));
    }

    /**
     * Refreshes the given token.
     *
     * @param token the token.
     * @return the new token or null if any error occured.
     */
    public String refreshToken(final String token) {
        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            claims.setIssuedAt(new Date());
            return generateToken(claims);
        }
        return null;
    }

    /**
     * Validates the given token against a given <code>UserDetails</code> object, thus checking username, token
     * expiration and meanwhile password changes.
     *
     * @param token       the token.
     * @param userDetails userDetails the <code>UserDetails</code> used for validation.
     * @return true, if the given token is valid.
     */
    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        if (userDetails == null || username == null || created == null) {
            return false;
        }
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
