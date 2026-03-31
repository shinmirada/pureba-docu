package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un rol del sistema")
public class RoleDto {

    @Schema(description = "ID único del rol", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long roleId;

    @Schema(description = "ID de la organización a la que pertenece el rol",
            example = "1", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Long organizationId;

    @Schema(description = "Nombre de la organización", example = "Tech Corp S.A.S",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "Nombre del rol", example = "ADMIN",
            allowableValues = {"ADMIN", "USER"}, required = true)
    private String name;

    @Schema(description = "Descripción del rol",
            example = "Administrador con acceso completo a la plataforma")
    private String description;
}
