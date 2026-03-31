package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Asignación de rol a usuario")
public class UserRoleDto {

    @Schema(description = "ID único de la asignación", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long userRoleId;

    @Schema(description = "ID del usuario al que se asigna el rol",
            example = "5", required = true)
    private Long userId;

    @Schema(description = "Nombre de usuario", example = "jdoe",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String username;

    @Schema(description = "ID del rol a asignar", example = "2", required = true)
    private Long roleId;

    @Schema(description = "Nombre del rol", example = "ADMIN",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String roleName;
}
