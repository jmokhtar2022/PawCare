package com.example.pawcare.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtService {

    @Value("${jwt.app.jwtSecret}")
    private String jwtSecreet;

    @Value("${jwt.app.jwtExpiration}")
    private int jwtExpiration;
}
