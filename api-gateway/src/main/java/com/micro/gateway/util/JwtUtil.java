package com.micro.gateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    public boolean isInvalid(final String token) {
        Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET))).build().parseClaimsJws(token);
        return true;
    }

}