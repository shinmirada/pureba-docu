package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de una cuenta de usuario")
public class UserAccountDto {

    @Schema(description = "ID único del usuario", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "ID de la organización a la que pertenece el usuario",
            example = "1", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Long organizationId;

    @Schema(description = "Nombre de la organización", example = "Tech Corp S.A.S",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "Nombre de usuario (único por organización)",
            example = "jdoe", required = true)
    private String username;

    @Schema(description = "Correo electrónico del usuario",
            example = "jdoe@techcorp.com", required = true)
    private String email;

    @Schema(description = "Contraseña del usuario (solo en creación, nunca se retorna)",
            example = "SecurePass123!", writeOnly = true)
    private String password;

    @Schema(description = "Nombre completo del usuario", example = "John Doe")
    private String fullName;

    @Schema(description = "Indica si el usuario está activo", example = "true")
    private Boolean active;

    @Schema(description = "Fecha y hora del último inicio de sesión",
            example = "2024-06-15T09:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime lastLogin;

    @Schema(description = "Fecha de creación del usuario",
            example = "2024-06-01T12:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Lista de roles asignados al usuario",
            accessMode = Schema.AccessMode.READ_ONLY)
    private List<RoleDto> roles;
}
