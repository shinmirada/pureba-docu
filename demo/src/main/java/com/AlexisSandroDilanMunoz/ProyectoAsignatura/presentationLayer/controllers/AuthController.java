package com.AlexisSandroDilanMunoz.ProyectoAsignatura.presentationLayer.controllers;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.LoginRequestDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.LoginResponseDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.AuthService;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la autenticación y gestión de acceso.
 *
 * CARACTERÍSTICAS:
 * - Registro de organizaciones
 * - Inicio de sesión de usuarios
 * - Cierre de sesión mediante token
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // Inyección del servicio mediante constructor
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registrar una nueva organización en el sistema
     */
    @PostMapping("/register")
    public OrganizationDto registerOrganization(
            @RequestBody OrganizationCreateDto createDto) {

        return authService.registerOrganization(createDto);
    }

    /**
     * Iniciar sesión en el sistema
     */
    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto loginRequest) {

        return authService.login(loginRequest);
    }

    /**
     * Cerrar sesión del usuario autenticado
     */
    @PostMapping("/logout")
    public void logout(
            @RequestHeader("Authorization") String token) {

        authService.logout(token);
    }
}