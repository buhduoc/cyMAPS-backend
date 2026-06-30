package com.cymap.ms_historial.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cymap.ms_historial.DTO.LoginJWTDTO;

@Service
public class AuthService {

    private final String secretKey = "mi_clave_secreta";

    public String generarToken(LoginJWTDTO loginDTO) {

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(loginDTO.getUsername())
                .withClaim("roles", List.of("ROLE_USER"))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);
    }
}
