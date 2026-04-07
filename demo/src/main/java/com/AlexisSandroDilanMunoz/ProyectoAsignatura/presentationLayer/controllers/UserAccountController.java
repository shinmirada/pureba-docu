package com.AlexisSandroDilanMunoz.ProyectoAsignatura.presentationLayer.controllers;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountResponseDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountUpdateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.UserAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios del sistema.
 *
 * CARACTERÍSTICAS:
 * - Creación y actualización de usuarios
 * - Activación y desactivación de cuentas
 * - Asignación y revocación de roles
 * - Consulta de usuarios por id y por organización
 */
@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    private final UserAccountService userAccountService;

    // Inyección del servicio mediante constructor
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    /**
     * Crear un nuevo usuario
     */
    @PostMapping
    public UserAccountResponseDto createUser(
            @RequestBody UserAccountCreateDto createDto,
            @RequestParam Long organizationId) {

        return userAccountService.createUser(createDto, organizationId);
    }

    /**
     * Actualizar un usuario existente
     */
    @PutMapping("/{userId}")
    public UserAccountResponseDto updateUser(
            @PathVariable Long userId,
            @RequestBody UserAccountUpdateDto updateDto) {

        return userAccountService.updateUser(userId, updateDto);
    }

    /**
     * Activar un usuario
     */
    @PutMapping("/{userId}/activate")
    public void activateUser(@PathVariable Long userId) {
        userAccountService.activateUser(userId);
    }

    /**
     * Desactivar un usuario
     */
    @PutMapping("/{userId}/deactivate")
    public void deactivateUser(@PathVariable Long userId) {
        userAccountService.deactivateUser(userId);
    }

    /**
     * Asignar un rol a un usuario
     */
    @PutMapping("/{userId}/assign-role")
    public void assignRole(
            @PathVariable Long userId,
            @RequestParam Long roleId) {

        userAccountService.assignRole(userId, roleId);
    }

    /**
     * Revocar un rol de un usuario
     */
    @PutMapping("/{userId}/revoke-role")
    public void revokeRole(
            @PathVariable Long userId,
            @RequestParam Long roleId) {

        userAccountService.revokeRole(userId, roleId);
    }

    /**
     * Obtener un usuario por su id
     */
    @GetMapping("/{userId}")
    public UserAccountResponseDto getUserById(@PathVariable Long userId) {
        return userAccountService.getUserById(userId);
    }

    /**
     * Obtener usuarios de una organización
     */
    @GetMapping("/organization/{organizationId}")
    public List<UserAccountResponseDto> getUsersByOrganization(
            @PathVariable Long organizationId) {

        return userAccountService.getUsersByOrganization(organizationId);
    }
}