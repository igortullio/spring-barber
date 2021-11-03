package com.igortullio.barber.adapter.config.filter;

import com.igortullio.barber.adapter.database.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private static final String JWT_SECRET = "zdtlD3JK56m6wTTgsNFhqzjqPzdtlD3JK56m6wTTgsNFhqzjqP";
    private static final String JWT_ISSUER = "com.igortullio";

    private final Logger logger;

    public String generateAccessToken(UserEntity userEntity) {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(format("%s,%s", userEntity.getId(), userEntity.getUsername()))
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .signWith(key)
                .compact();
    }

    public String getUserId(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build();

        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build();

        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build();

        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET)
                    .build();

            jwtParser.parseClaimsJws(token).getBody();
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
