package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para registrar una nueva organización y su administrador inicial")
public class OrganizationCreateDto {

    @Schema(description = "Nombre de la organización (único en la plataforma)",
            example = "Tech Corp S.A.S",
            required = true,
            maxLength = 200)
    private String name;

    @Schema(description = "Dominio de la organización (único en la plataforma)",
            example = "techcorp.com",
            maxLength = 200)
    private String domain;

    @Schema(description = "Nombre de usuario del administrador inicial",
            example = "admin.techcorp",
            required = true,
            maxLength = 100)
    private String adminUsername;

    @Schema(description = "Correo electrónico del administrador inicial",
            example = "admin@techcorp.com",
            required = true,
            maxLength = 200)
    private String adminEmail;

    @Schema(description = "Contraseña del administrador inicial",
            example = "AdminSecure123!",
            required = true)
    private String adminPassword;

    @Schema(description = "Nombre completo del administrador inicial",
            example = "Carlos Rodríguez",
            maxLength = 200)
    private String adminFullName;
}
