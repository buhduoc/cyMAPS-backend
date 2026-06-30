package com.cymap.ms_historial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cymap.ms_historial.DTO.LoginJWTDTO;
import com.cymap.ms_historial.DTO.ResponseDTO;
import com.cymap.ms_historial.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginJWTDTO loginDTO) {

        String token = authService.generarToken(loginDTO);

        ResponseDTO response = new ResponseDTO();
        response.setRespuestaText(token);
        response.setRespuestaInteger(200);

        return response;
    }
}
