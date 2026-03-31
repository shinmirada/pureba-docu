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
@Schema(description = "Información de un usuario retornada por el servidor (sin contraseña)")
public class UserAccountResponseDto {

    @Schema(description = "ID único del usuario", example = "5",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "ID de la organización a la que pertenece",
            example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long organizationId;

    @Schema(description = "Nombre de la organización", example = "Tech Corp S.A.S",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "Nombre de usuario", example = "jdoe",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String username;

    @Schema(description = "Correo electrónico", example = "jdoe@techcorp.com",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String email;

    @Schema(description = "Nombre completo", example = "John Doe",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String fullName;

    @Schema(description = "Indica si el usuario está activo", example = "true",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean active;

    @Schema(description = "Fecha y hora del último inicio de sesión",
            example = "2024-06-15T09:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime lastLogin;

    @Schema(description = "Fecha de creación del usuario",
            example = "2024-06-01T12:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Roles asignados al usuario",
            accessMode = Schema.AccessMode.READ_ONLY)
    private List<RoleDto> roles;
}
