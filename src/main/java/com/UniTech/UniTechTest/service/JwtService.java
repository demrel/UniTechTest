package com.UniTech.UniTechTest.service;



import com.UniTech.UniTechTest.model.User;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUserName(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims,
                         User user);



    boolean isTokenValid(String token, User user);





}
