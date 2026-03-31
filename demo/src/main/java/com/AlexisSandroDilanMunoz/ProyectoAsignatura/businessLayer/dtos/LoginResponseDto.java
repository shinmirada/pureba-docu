package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta al iniciar sesión correctamente — incluye el token JWT")
public class LoginResponseDto {

    @Schema(description = "Token JWT para autenticar las siguientes peticiones",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String token;

    @Schema(description = "Tipo de token (siempre Bearer)",
            example = "Bearer",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String tokenType;

    @Schema(description = "ID del usuario autenticado", example = "5",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "Nombre de usuario", example = "jdoe",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String username;

    @Schema(description = "Nombre completo del usuario", example = "John Doe",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String fullName;

    @Schema(description = "ID de la organización del usuario autenticado",
            example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long organizationId;

    @Schema(description = "Nombre de la organización", example = "Tech Corp S.A.S",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "Roles del usuario (para construir el menú dinámico en el frontend)",
            example = "[\"ADMIN\"]", accessMode = Schema.AccessMode.READ_ONLY)
    private List<String> roles;
}
