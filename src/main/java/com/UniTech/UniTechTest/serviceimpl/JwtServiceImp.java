package com.UniTech.UniTechTest.serviceimpl;

import com.UniTech.UniTechTest.model.User;
import com.UniTech.UniTechTest.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtServiceImp implements JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(User user) {
        return generateToken(Map.of("userName", user.getUsername()), user);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims,
                                User user) {
        return buildToken(extraClaims, user, jwtExpiration);
    }

    @Override
    public boolean isTokenValid(String token, User user) {
        final String userId = extractUserName(token);
        return (userId.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private String buildToken(Map<String, Object> extraClaims,
                              User user,
                              long expiration) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setId(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}