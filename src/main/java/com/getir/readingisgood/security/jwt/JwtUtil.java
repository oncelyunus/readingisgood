package com.getir.readingisgood.security.jwt;

import com.getir.readingisgood.security.service.GetirUserDetail;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Service
@Log4j2
public class JwtUtil {

        @Value("${getir.app.jwtSecret}")
        private String jwtSecret;

        @Value("${getir.app.jwtExpirationMs}")
        private int jwtExpirationMs;

        private Key hashedSecretKey;

        @PostConstruct
        public void setup() {
                final byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
                this.hashedSecretKey = Keys.hmacShaKeyFor(keyBytes);
        }

        public String generateJwtToken(Authentication authentication) {

                GetirUserDetail userPrincipal = (GetirUserDetail) authentication.getPrincipal();
                final Date now = new Date();
                return Jwts.builder()
                        .setSubject((userPrincipal.getEmail()))
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + jwtExpirationMs))
                        .signWith(hashedSecretKey, SignatureAlgorithm.HS512)
                        .compact();
        }

        public String parseUsername(String token) {
                return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        }

        public boolean validateJwtToken(String authToken) {
                try {
                        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
                        return true;
                } catch (SignatureException e) {
                        log.error("Invalid JWT signature: {}", e.getMessage());
                } catch (MalformedJwtException e) {
                        log.error("Invalid JWT token: {}", e.getMessage());
                } catch (ExpiredJwtException e) {
                        log.error("JWT token is expired: {}", e.getMessage());
                } catch (UnsupportedJwtException e) {
                        log.error("JWT token is unsupported: {}", e.getMessage());
                } catch (IllegalArgumentException e) {
                        log.error("JWT claims string is empty: {}", e.getMessage());
                }
                return false;
        }
}
